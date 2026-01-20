package it.gov.pagopa.pu.worker.config.temporal.tracing;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.otel.bridge.EventListener;
import io.micrometer.tracing.otel.bridge.EventPublishingContextWrapper;
import io.opentelemetry.opentracingshim.SpanShimHolder;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import org.jspecify.annotations.Nullable;
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
  private final Tracer tracer;

  public Slf4jEventListenerSupport(TracingProperties tracingProperties, Tracer tracer) {
    this.correlationFields = tracingProperties.getBaggage().getCorrelation().getFields();
    this.tracer = tracer;
  }

  @Override
  public void onEvent(Object event) {
    switch (event) {
      case
        EventPublishingContextWrapper.ScopeAttachedEvent scopeAttachedEvent when scopeAttachedEvent.getSpan() == null ->
        setContext();
      case
        EventPublishingContextWrapper.ScopeRestoredEvent scopeRestoredEvent when scopeRestoredEvent.getSpan() == null ->
        setContext();
      default -> {
        if (event instanceof EventPublishingContextWrapper.ScopeClosedEvent) {
          io.micrometer.tracing.Span span = tracer.currentSpan();
          if (span != null) {
            span.abandon();
          }
        }
      }
    }

  }

  private void setContext() {
    Span currentSpan = SpanShimHolder.getCurrentSpan();
    if (currentSpan != null) {
      SpanContext currentSpanContext = currentSpan.context();
      String traceId = currentSpanContext.toTraceId();
      String spanId = currentSpanContext.toSpanId();

      MDC.put("traceId", traceId);
      MDC.put("spanId", spanId);

      io.micrometer.tracing.Span.Builder otSpanBuilder = tracer.spanBuilder()
        .setParent(new TraceContext() {

          @Override
          public @Nullable String parentId() {
            return traceId;
          }

          @Override
          public String traceId() {
            return traceId;
          }

          @Override
          public String spanId() {
            return spanId;
          }

          @Override
          public Boolean sampled() {
            return false;
          }
        });

      currentSpanContext.baggageItems().forEach(i -> {
        if (correlationFields.contains(i.getKey())) {
          MDC.put(i.getKey(), i.getValue());
          otSpanBuilder.tag(i.getKey(), i.getValue());
        }
      });

      otSpanBuilder
        .start();
    }
  }
}
