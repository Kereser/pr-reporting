package co.com.crediya.reporting.usecase.getapprovedreportinfo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GetApprovedReportInfoUseCaseImpTest {

  @Mock private ApprovedReportRepository approvedReportRepository;

  @InjectMocks private GetApprovedReportInfoUseCaseImp useCase;

  @Test
  void shouldReturnApprovedReportSuccessfully() {
    ApprovedReport expectedReport = new ApprovedReport();
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.just(expectedReport));

    Mono<ApprovedReport> resultMono = useCase.execute();

    StepVerifier.create(resultMono).expectNext(expectedReport).verifyComplete();

    verify(approvedReportRepository, times(1)).getApprovedReport();
  }

  @Test
  void shouldPropagateErrorWhenRepositoryFails() {
    RuntimeException repositoryError = new RuntimeException("Database error");
    when(approvedReportRepository.getApprovedReport()).thenReturn(Mono.error(repositoryError));

    Mono<ApprovedReport> resultMono = useCase.execute();

    StepVerifier.create(resultMono).expectErrorMatches(error -> error == repositoryError).verify();
  }
}
