package it.gov.pagopa.pu.worker.util;


import org.slf4j.MDC;

public class UtilitiesTest {

  public static void setTraceId(String traceId) {
    setTraceId(traceId, null);
  }
  public static void setTraceId(String traceId, String spanId) {
    MDC.put("traceId", traceId);
    MDC.put("spanId", spanId);
  }
  public static void clearTraceIdContext(){
    MDC.clear();
  }

}
