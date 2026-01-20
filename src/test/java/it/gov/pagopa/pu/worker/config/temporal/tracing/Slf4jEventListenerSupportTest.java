package it.gov.pagopa.pu.worker.config.temporal.tracing;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.otel.bridge.EventPublishingContextWrapper;
import io.opentelemetry.api.trace.Span;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.boot.micrometer.tracing.autoconfigure.TracingProperties;

@ExtendWith(MockitoExtension.class)
class Slf4jEventListenerSupportTest {

  @Mock
  private TracingProperties tracingPropertiesMock;
  @Mock
  private Tracer tracerMock;

  private Slf4jEventListenerSupport slf4jEventListenerSupport;

  @BeforeEach
  void init(){
    slf4jEventListenerSupport = new Slf4jEventListenerSupport(tracingPropertiesMock, tracerMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(tracingPropertiesMock, tracerMock);
  }

  @Test
  void givenOtelSpanAndAttachEventWhenOnEventThenDoNothing() {
    try(MockedStatic<MDC> mdcMock = Mockito.mockStatic(MDC.class)) {
      EventPublishingContextWrapper.ScopeAttachedEvent event = Mockito.mock(EventPublishingContextWrapper.ScopeAttachedEvent.class);
      Mockito.when(event.getSpan())
        .thenReturn(Mockito.mock(Span.class));

      slf4jEventListenerSupport.onEvent(event);

      mdcMock.verifyNoInteractions();
    }
  }

  @Test
  void givenOtelSpanAndRestoredEventWhenOnEventThenDoNothing() {
    try(MockedStatic<MDC> mdcMock = Mockito.mockStatic(MDC.class)) {
      EventPublishingContextWrapper.ScopeRestoredEvent event = Mockito.mock(EventPublishingContextWrapper.ScopeRestoredEvent.class);
      Mockito.when(event.getSpan())
        .thenReturn(Mockito.mock(Span.class));

      slf4jEventListenerSupport.onEvent(event);

      mdcMock.verifyNoInteractions();
    }
  }
}
