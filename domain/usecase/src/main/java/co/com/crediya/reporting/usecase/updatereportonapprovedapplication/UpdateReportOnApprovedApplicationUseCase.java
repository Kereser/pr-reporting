package co.com.crediya.reporting.usecase.updatereportonapprovedapplication;

import co.com.crediya.reporting.model.approvedreport.dto.ApplicationSummaryDTOInput;
import reactor.core.publisher.Mono;

public interface UpdateReportOnApprovedApplicationUseCase {
  Mono<Void> execute(ApplicationSummaryDTOInput dtoInput);
}
