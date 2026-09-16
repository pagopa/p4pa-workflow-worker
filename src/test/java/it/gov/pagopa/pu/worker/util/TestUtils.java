package it.gov.pagopa.pu.worker.util;

import it.gov.pagopa.payhub.activities.util.Utilities;

import java.util.Locale;
import java.util.TimeZone;

public class TestUtils {

  private TestUtils() {}

  static {
    clearDefaultTimezone();
    clearLocale();
  }

  public static void clearDefaultTimezone() {
    TimeZone.setDefault(Utilities.DEFAULT_TIMEZONE);
  }

  public static void clearLocale() {
    Locale.setDefault(Locale.ITALY);
  }
}
