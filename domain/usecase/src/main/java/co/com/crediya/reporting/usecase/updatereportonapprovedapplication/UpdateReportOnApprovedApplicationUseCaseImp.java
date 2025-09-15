package co.com.crediya.reporting.usecase.updatereportonapprovedapplication;

import co.com.crediya.reporting.model.approvedreport.dto.ApplicationSummaryDTOInput;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateReportOnApprovedApplicationUseCaseImp
    implements UpdateReportOnApprovedApplicationUseCase {

  private final ApprovedReportRepository approvedReportRepository;

  @Override
  public Mono<Void> execute(ApplicationSummaryDTOInput dtoInput) {
    return Mono.zip(
            approvedReportRepository.incrementApprovedCount(),
            approvedReportRepository.addApprovedAmount(dtoInput.getAmount()))
        .then();
  }
}
