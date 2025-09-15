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
  public static class Docs {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ApproveReport {
      public static final String GET_SUMMARY = "Get metrics for approved reports";
      public static final String GET_DESCRIPTION = "Metrics about approved reports";
      public static final String GET_OPERATION_ID = "getApprovedReports";
    }
  }
}
