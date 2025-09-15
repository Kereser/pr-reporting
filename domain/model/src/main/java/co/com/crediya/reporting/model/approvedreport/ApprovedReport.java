package co.com.crediya.reporting.model.approvedreport;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApprovedReport {
  private long totalCount;
  private Instant countLastUpdated;
  private BigDecimal totalAmount;
  private Instant amountLastUpdated;
}
