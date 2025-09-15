package co.com.crediya.reporting.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestConstants {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ReportAPI {
    public static final String APPROVED_URL = "/api/v1/approve";
  }
}
