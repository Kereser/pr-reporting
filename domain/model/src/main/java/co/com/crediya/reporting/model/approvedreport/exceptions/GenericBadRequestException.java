package co.com.crediya.reporting.model.approvedreport.exceptions;

public class GenericBadRequestException extends BusinessException {
  private static final String BASE_MSG = "Bad information for incoming request";

  public GenericBadRequestException(String attribute, String reason) {
    super(BASE_MSG, attribute, reason);
  }
}
