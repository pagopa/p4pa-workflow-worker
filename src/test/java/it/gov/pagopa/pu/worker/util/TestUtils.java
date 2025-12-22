package it.gov.pagopa.pu.worker.util;

import it.gov.pagopa.payhub.activities.util.Utilities;

import java.util.TimeZone;

public class TestUtils {
  static {
    clearDefaultTimezone();
  }

  public static void clearDefaultTimezone() {
    TimeZone.setDefault(Utilities.DEFAULT_TIMEZONE);
  }
}
