package co.com.crediya.reporting.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import co.com.crediya.reporting.api.RestConstants;
import co.com.crediya.reporting.api.config.security.implementations.JwtSecurityContextRepository;
import co.com.crediya.reporting.model.approvedreport.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
  private final JwtSecurityContextRepository securityContextRepository;

  public static final String SWAGGER_PATH = "/swagger-ui.html";
  public static final String SWAGGER_PATH_1 = "/swagger-ui/**";
  public static final String SWAGGER_PATH_2 = "/v3/api-docs/**";
  public static final String ACTUATOR_PATHS = "/actuator/**";

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .securityContextRepository(securityContextRepository)
        .exceptionHandling(this::configureExceptionHandling)
        .authorizeExchange(this::configureAuthorization)
        .build();
  }

  private void configureExceptionHandling(
      ServerHttpSecurity.ExceptionHandlingSpec exceptionHandling) {
    exceptionHandling
        .authenticationEntryPoint(
            (swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
        .accessDeniedHandler(
            (swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)));
  }

  private void configureAuthorization(ServerHttpSecurity.AuthorizeExchangeSpec exchanges) {
    exchanges
        .pathMatchers(SWAGGER_PATH)
        .permitAll()
        .pathMatchers(SWAGGER_PATH_1)
        .permitAll()
        .pathMatchers(SWAGGER_PATH_2)
        .permitAll()
        .pathMatchers(ACTUATOR_PATHS)
        .permitAll()
        .pathMatchers(HttpMethod.GET, RestConstants.ReportAPI.APPROVED_URL)
        .hasAnyAuthority(CommonConstants.Security.ADMIN_ROLE)
        .anyExchange()
        .authenticated();
  }
}
