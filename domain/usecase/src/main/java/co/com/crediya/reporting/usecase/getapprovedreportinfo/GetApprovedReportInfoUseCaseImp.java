package co.com.crediya.reporting.usecase.getapprovedreportinfo;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetApprovedReportInfoUseCaseImp implements GetApprovedReportInfoUseCase {

  private final ApprovedReportRepository approvedReportRepository;

  @Override
  public Mono<ApprovedReport> execute() {
    return approvedReportRepository.getApprovedReport();
  }
}
