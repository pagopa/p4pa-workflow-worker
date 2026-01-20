package it.gov.pagopa.pu.worker.config.temporal.tracing;

import io.micrometer.tracing.otel.bridge.EventListener;
import io.micrometer.tracing.otel.bridge.EventPublishingContextWrapper;
import io.opentelemetry.opentracingshim.SpanShimHolder;
import io.opentracing.SpanContext;
import org.slf4j.MDC;
import org.springframework.boot.micrometer.tracing.autoconfigure.TracingProperties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An EventListener that replaces Slf4JEventListener MDC context configuration only if the span is null in the event.<BR />
 * Necessary because applicationInsight will prevent OTel to log the spans.
 */
@Service
public class Slf4jEventListenerSupport implements EventListener {

  private final List<String> correlationFields;

  public Slf4jEventListenerSupport(TracingProperties tracingProperties) {
    this.correlationFields = tracingProperties.getBaggage().getCorrelation().getFields();
  }

  @Override
  public void onEvent(Object event) {
    if (event instanceof EventPublishingContextWrapper.ScopeAttachedEvent scopeAttachedEvent) {
      if(scopeAttachedEvent.getSpan()==null){
        setContext();
      }
    }
    else if (event instanceof EventPublishingContextWrapper.ScopeRestoredEvent scopeRestoredEvent
      && scopeRestoredEvent.getSpan()==null){
        setContext();
      }
  }

  private void setContext() {
    SpanContext currentSpanContext = SpanShimHolder.getCurrentSpan().context();
    MDC.put("traceId", currentSpanContext.toTraceId());
    MDC.put("spanId", currentSpanContext.toSpanId());

    currentSpanContext.baggageItems().forEach(i -> {
      if(correlationFields.contains(i.getKey())){
        MDC.put(i.getKey(), i.getValue());
      }
    });
  }
}
