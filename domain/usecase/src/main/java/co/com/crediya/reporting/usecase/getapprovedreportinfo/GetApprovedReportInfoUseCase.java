package co.com.crediya.reporting.usecase.getapprovedreportinfo;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import reactor.core.publisher.Mono;

public interface GetApprovedReportInfoUseCase {
  Mono<ApprovedReport> execute();
}
