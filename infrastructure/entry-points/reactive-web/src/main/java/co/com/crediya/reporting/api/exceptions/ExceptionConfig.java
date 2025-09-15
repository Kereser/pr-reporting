package co.com.crediya.reporting.api.exceptions;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import co.com.crediya.reporting.model.approvedreport.exceptions.GenericBadRequestException;

@Configuration
public class ExceptionConfig {

  @Bean
  public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
    return Map.of(GenericBadRequestException.class, HttpStatus.BAD_REQUEST);
  }

  @Bean
  public WebProperties.Resources webResources() {
    return new WebProperties.Resources();
  }

  @Bean
  public HttpStatus defaultStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
