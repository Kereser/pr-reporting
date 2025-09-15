package co.com.crediya.reporting.sqs.listener;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.crediya.reporting.model.approvedreport.dto.ApplicationSummaryDTOInput;
import co.com.crediya.reporting.model.approvedreport.exceptions.GenericBadRequestException;
import co.com.crediya.reporting.usecase.updatereportonapprovedapplication.UpdateReportOnApprovedApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSProcessor implements Function<Message, Mono<Void>> {
  private final UpdateReportOnApprovedApplicationUseCase useCase;
  private final ObjectMapper objectMapper;

  private static final String ERROR_WHEN_APPLY_PROCESSOR = "Error parsing SQS message ";

  @Override
  public Mono<Void> apply(Message message) {
    try {
      ApplicationSummaryDTOInput event =
          objectMapper.readValue(message.body(), ApplicationSummaryDTOInput.class);

      return useCase.execute(event);
    } catch (Exception e) {
      log.error("Failed to process SQS message. Raw body: {}", message.body(), e);

      return Mono.error(
          new GenericBadRequestException(
              ApplicationSummaryDTOInput.class.getSimpleName(),
              ERROR_WHEN_APPLY_PROCESSOR + e.getMessage()));
    }
  }
}
