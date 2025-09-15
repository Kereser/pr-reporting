package co.com.crediya.reporting.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestConstants {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class StatusCodeInt {
    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String CONFLICT = "409";
    public static final String SERVER_ERROR = "500";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ReportAPI {
    public static final String APPROVED_URL = "/reporting/api/v1/approve";
  }
}
