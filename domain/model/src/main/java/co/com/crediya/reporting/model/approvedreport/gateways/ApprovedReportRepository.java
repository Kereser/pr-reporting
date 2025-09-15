package co.com.crediya.reporting.model.approvedreport.gateways;

import java.math.BigDecimal;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import reactor.core.publisher.Mono;

public interface ApprovedReportRepository {
  Mono<Void> incrementApprovedCount();

  Mono<Void> addApprovedAmount(BigDecimal amount);

  Mono<ApprovedReport> getApprovedReport();
}
