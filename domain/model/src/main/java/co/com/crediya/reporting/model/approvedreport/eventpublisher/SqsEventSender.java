package co.com.crediya.reporting.model.approvedreport.eventpublisher;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import reactor.core.publisher.Mono;

public interface SqsEventSender {
  Mono<Void> sentEventoToApprovedRelatedReportDailyQueue(ApprovedReport report);
}
