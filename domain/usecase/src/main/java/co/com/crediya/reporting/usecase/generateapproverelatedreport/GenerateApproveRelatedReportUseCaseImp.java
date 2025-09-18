package co.com.crediya.reporting.usecase.generateapproverelatedreport;

import co.com.crediya.reporting.model.approvedreport.eventpublisher.SqsEventSender;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GenerateApproveRelatedReportUseCaseImp implements GenerateApproveRelatedReportUseCase {

  private final ApprovedReportRepository approvedReportRepository;
  private final SqsEventSender sqsEventSender;

  @Override
  public Mono<Void> execute() {
    return approvedReportRepository
        .getApprovedReport()
        .flatMap(sqsEventSender::sentEventoToApprovedRelatedReportDailyQueue)
        .then();
  }
}
