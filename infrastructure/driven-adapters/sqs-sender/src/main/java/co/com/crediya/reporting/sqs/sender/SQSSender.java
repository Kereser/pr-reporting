package co.com.crediya.reporting.sqs.sender;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import co.com.crediya.reporting.model.approvedreport.eventpublisher.SqsEventSender;
import co.com.crediya.reporting.sqs.sender.config.SQSSenderProperties;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Log4j2
public class SQSSender implements SqsEventSender {
  private final SQSSenderProperties properties;
  private final SqsAsyncClient client;
  private final ObjectMapper mapper;

  public SQSSender(
      SQSSenderProperties properties,
      @Qualifier(value = "configSenderSqs") SqsAsyncClient client,
      ObjectMapper mapper) {
    this.properties = properties;
    this.client = client;
    this.mapper = mapper;
  }

  private static final String APPROVE_RELATED_REPORT_SUCCESS =
      "Sending msg to approved related daily report queue. Msg id {}";
  private static final String APPROVE_RELATED_REPORT_ERROR =
      "Unable to send message to approved related daily report queue.";

  private record ApprovedReportDTO(
      long count, Instant countUpdated, BigDecimal amount, Instant amountUpdated) {}

  @Override
  public Mono<Void> sentEventoToApprovedRelatedReportDailyQueue(ApprovedReport report) {
    return Mono.fromCallable(
            () -> {
              ApprovedReportDTO dto =
                  new ApprovedReportDTO(
                      report.getTotalCount(),
                      report.getCountLastUpdated(),
                      report.getTotalAmount(),
                      report.getAmountLastUpdated());

              return mapper.writeValueAsString(dto);
            })
        .flatMap(
            msg -> {
              SendMessageRequest req =
                  SendMessageRequest.builder()
                      .queueUrl(properties.approvedRelatedDailyReportQueue())
                      .messageBody(msg)
                      .build();

              return Mono.fromFuture(client.sendMessage(req));
            })
        .doOnNext(res -> log.info(APPROVE_RELATED_REPORT_SUCCESS, res.messageId()))
        .then()
        .doOnError(err -> log.error(APPROVE_RELATED_REPORT_ERROR, err));
  }
}
