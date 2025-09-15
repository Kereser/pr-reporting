package co.com.crediya.reporting.api.helper;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

import java.util.function.Consumer;

import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.MediaType;
import org.springframework.web.ErrorResponse;

import co.com.crediya.reporting.api.RestConstants;
import co.com.crediya.reporting.api.dto.ApprovedReportDTOResponse;
import co.com.crediya.reporting.api.exceptions.ErrorResponseDTO;
import co.com.crediya.reporting.model.approvedreport.CommonConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportingAPIDocs {

  public static Consumer<Builder> getApplicationsForManualReview() {
    return builder ->
        builder
            .summary(CommonConstants.Docs.ApproveReport.GET_SUMMARY)
            .description(CommonConstants.Docs.ApproveReport.GET_DESCRIPTION)
            .operationId(CommonConstants.Docs.ApproveReport.GET_OPERATION_ID)
            .response(
                responseBuilder()
                    .responseCode(RestConstants.StatusCodeInt.OK)
                    .content(
                        contentBuilder()
                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                            .schema(
                                schemaBuilder().implementation(ApprovedReportDTOResponse.class))))
            .response(
                responseBuilder()
                    .responseCode(RestConstants.StatusCodeInt.BAD_REQUEST)
                    .content(
                        contentBuilder()
                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                            .schema(schemaBuilder().implementation(ErrorResponseDTO.class))))
            .response(
                responseBuilder()
                    .responseCode(RestConstants.StatusCodeInt.SERVER_ERROR)
                    .content(
                        contentBuilder()
                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                            .schema(schemaBuilder().implementation(ErrorResponse.class))));
  }
}
