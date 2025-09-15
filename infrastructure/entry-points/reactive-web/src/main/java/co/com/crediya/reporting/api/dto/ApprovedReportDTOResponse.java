package co.com.crediya.reporting.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ApprovedReportDTOResponse(Approved approved) {
  public record Approved(Count count, Amount amount) {
    public record Count(long value, Instant updatedAt) {}

    public record Amount(BigDecimal value, Instant updatedAt) {}
  }
}
