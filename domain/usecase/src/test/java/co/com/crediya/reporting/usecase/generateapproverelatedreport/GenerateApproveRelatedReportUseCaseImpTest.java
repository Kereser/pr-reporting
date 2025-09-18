package co.com.crediya.reporting.usecase.generateapproverelatedreport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import co.com.crediya.reporting.model.approvedreport.eventpublisher.SqsEventSender;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GenerateApproveRelatedReportUseCaseImpTest {
  static {
    BlockHound.install();
  }

  @Mock private ApprovedReportRepository approvedReportRepository;

  @Mock private SqsEventSender sqsEventSender;

  @InjectMocks private GenerateApproveRelatedReportUseCaseImp useCase;

  @Captor private ArgumentCaptor<ApprovedReport> reportCaptor;

  private ApprovedReport approvedReport;

  @BeforeEach
  void setUp() {
    approvedReport =
        ApprovedReport.builder()
            .totalCount(1)
            .countLastUpdated(Instant.now())
            .totalAmount(BigDecimal.valueOf(1_000_000))
            .amountLastUpdated(Instant.now())
            .build();
  }

  @Test
  void shouldGetReportAndSendEventSuccessfully() {
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.just(approvedReport));
    when(sqsEventSender.sentEventoToApprovedRelatedReportDailyQueue(any(ApprovedReport.class)))
        .thenReturn(Mono.empty());

    Mono<Void> resultMono = useCase.execute();

    StepVerifier.create(resultMono).verifyComplete();

    verify(sqsEventSender).sentEventoToApprovedRelatedReportDailyQueue(reportCaptor.capture());
    verify(sqsEventSender, times(1))
        .sentEventoToApprovedRelatedReportDailyQueue(reportCaptor.getValue());
    assertThat(reportCaptor.getValue()).isEqualTo(approvedReport);
  }

  @Test
  void shouldReturnErrorWhenRepositoryFails() {
    RuntimeException dbError = new RuntimeException("Database connection failed");
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.error(dbError));

    Mono<Void> resultMono = useCase.execute();

    StepVerifier.create(resultMono).expectErrorMatches(error -> error == dbError).verify();

    verify(sqsEventSender, never()).sentEventoToApprovedRelatedReportDailyQueue(any());
  }

  @Test
  void shouldReturnErrorWhenSqsSenderFails() {
    RuntimeException sqsError = new RuntimeException("SQS connection failed");
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.just(approvedReport));
    when(sqsEventSender.sentEventoToApprovedRelatedReportDailyQueue(any(ApprovedReport.class)))
        .thenReturn(Mono.error(sqsError));

    Mono<Void> resultMono = useCase.execute();

    StepVerifier.create(resultMono).expectErrorMatches(error -> error == sqsError).verify();
  }

  @Test
  void shouldCompleteWithoutSendingEventWhenReportIsNotFound() {
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.empty());

    Mono<Void> resultMono = useCase.execute();

    StepVerifier.create(resultMono).verifyComplete();

    verify(sqsEventSender, never()).sentEventoToApprovedRelatedReportDailyQueue(any());
  }
}
