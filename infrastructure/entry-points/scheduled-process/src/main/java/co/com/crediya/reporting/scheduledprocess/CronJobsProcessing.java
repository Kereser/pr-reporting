package co.com.crediya.reporting.scheduledprocess;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.com.crediya.reporting.usecase.generateapproverelatedreport.GenerateApproveRelatedReportUseCaseImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CronJobsProcessing {
  private final GenerateApproveRelatedReportUseCaseImp useCase;

  private static final String APPROVE_RELATED_ERROR =
      "Error while sending approve related daily report";
  private static final String APPROVE_RELATED_COMPLETE =
      "Approved related report was successfully triggered";

  @Scheduled(cron = CronExpressions.APPROVED_RELATED_REPORT_SCHEDULED)
  public void triggerApproveRelatedReport() {
    useCase
        .execute()
        .subscribe(
            null,
            error -> log.error(APPROVE_RELATED_ERROR, error),
            () -> log.info(APPROVE_RELATED_COMPLETE));
  }
}
