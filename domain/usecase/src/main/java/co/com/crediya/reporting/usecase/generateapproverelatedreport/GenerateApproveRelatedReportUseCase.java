package co.com.crediya.reporting.usecase.generateapproverelatedreport;

import reactor.core.publisher.Mono;

public interface GenerateApproveRelatedReportUseCase {
  Mono<Void> execute();
}
