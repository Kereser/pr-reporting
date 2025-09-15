package co.com.crediya.reporting.api;

import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.crediya.reporting.api.helper.ReportingAPIDocs;

@Configuration
public class RouterRest {
  @Bean
  public RouterFunction<ServerResponse> routerFunction(Handler handler) {
    return SpringdocRouteBuilder.route()
        .GET(
            RestConstants.ReportAPI.APPROVED_URL,
            req -> handler.listGETApproveReport(),
            ops -> ReportingAPIDocs.getApplicationsForManualReview().accept(ops))
        .build();
  }
}
