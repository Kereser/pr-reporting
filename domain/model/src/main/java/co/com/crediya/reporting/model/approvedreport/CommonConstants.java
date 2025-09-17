package co.com.crediya.reporting.model.approvedreport;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class StatisticItem {
    public static final String TABLE_NAME = "ApprovedApplicationStatistics";
    public static final String COUNT_KEY = "TOTAL_APPROVED_COUNT";
    public static final String AMOUNT_KEY = "TOTAL_APPROVED_AMOUNT";
    public static final String KEY_FIELD_NAME = "statisticId";
    public static final String UPDATE_COUNTER_QUERY = "ADD #val :inc SET #lu = :ts";
    public static final String VALUE_FIELD_NAME = "value";
    public static final String LAST_UPDATED_FIELD_NAME = "lastUpdated";
    public static final String VALUE_PLACEHOLDER = "#val";
    public static final String LAST_UPDATED_PLACEHOLDER = "#lu";
    public static final String INCREMENT_VALUE = ":inc";
    public static final String TIMESTAMP_VALUE = ":ts";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Security {
    public static final String BEARER = "Bearer ";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final int TOKEN_SUB_STR_LEN = 7;
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {
    public static final String EMAIL = "email";
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String AUTHORITIES = "authorities";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class ErrorResponse {
    // Examples
    public static final String ATTRIBUTE_DESCRIPTION = "Attribute or field";
    public static final String STATUS_DESCRIPTION = "Http status code";
    public static final String ERROR_DESCRIPTION = "Generic error for class";
    public static final String DETAILS_DESCRIPTION = "Detailed error";

    // Generic messages
    public static final String ERROR_EXAMPLE = "Field validation exception";
    public static final String DETAILS_EXAMPLE =
        "exampleValue is not a valid value for exampleAttribute";
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Docs {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ApproveReport {
      public static final String GET_SUMMARY = "Get metrics for approved reports";
      public static final String GET_DESCRIPTION = "Metrics about approved reports";
      public static final String GET_OPERATION_ID = "getApprovedReports";
    }
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Chars {
    public static final String EMPTY = "";
  }
}
