package co.com.crediya.reporting.usecase.updatereportonapprovedapplication;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.crediya.reporting.model.approvedreport.dto.ApplicationSummaryDTOInput;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateReportOnApprovedApplicationUseCaseImpTest {
  @Mock private ApprovedReportRepository approvedReportRepository;

  @InjectMocks private UpdateReportOnApprovedApplicationUseCaseImp useCase;

  private ApplicationSummaryDTOInput dtoInput;

  @BeforeEach
  void setUp() {
    dtoInput =
        new ApplicationSummaryDTOInput(
            UUID.randomUUID(), UUID.randomUUID(), new BigDecimal(5_000_000), 10);
  }

  @Test
  void shouldCompleteWhenBothRepositoryOperationsSucceed() {
    when(approvedReportRepository.incrementApprovedCount()).thenReturn(Mono.empty());
    when(approvedReportRepository.addApprovedAmount(dtoInput.getAmount())).thenReturn(Mono.empty());

    Mono<Void> resultMono = useCase.execute(dtoInput);

    StepVerifier.create(resultMono).verifyComplete();

    verify(approvedReportRepository).incrementApprovedCount();
    verify(approvedReportRepository).addApprovedAmount(dtoInput.getAmount());
  }

  @Test
  void shouldFailWhenIncrementCountFails() {
    RuntimeException repositoryError = new RuntimeException("Increment failed");
    when(approvedReportRepository.incrementApprovedCount()).thenReturn(Mono.error(repositoryError));
    when(approvedReportRepository.addApprovedAmount(dtoInput.getAmount())).thenReturn(Mono.empty());

    Mono<Void> resultMono = useCase.execute(dtoInput);

    StepVerifier.create(resultMono).expectErrorMatches(error -> error == repositoryError).verify();
  }
}
