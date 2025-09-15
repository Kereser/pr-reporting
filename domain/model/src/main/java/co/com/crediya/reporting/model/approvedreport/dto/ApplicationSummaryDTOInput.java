package co.com.crediya.reporting.model.approvedreport.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSummaryDTOInput {
  private UUID userId;
  private UUID id;
  private BigDecimal amount;
  private int applicationPeriod;
}
