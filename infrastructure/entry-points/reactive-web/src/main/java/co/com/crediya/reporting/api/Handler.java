package co.com.crediya.reporting.api;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.reporting.api.mapper.ApprovedReportRestMapper;
import co.com.crediya.reporting.usecase.getapprovedreportinfo.GetApprovedReportInfoUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
  private final GetApprovedReportInfoUseCase getApprovedReportInfoUseCase;
  private final ApprovedReportRestMapper restMapper;

  public Mono<ServerResponse> listGETApproveReport() {
    return getApprovedReportInfoUseCase
        .execute()
        .map(restMapper::toDTO)
        .flatMap(res -> ServerResponse.ok().bodyValue(res));
  }
}
