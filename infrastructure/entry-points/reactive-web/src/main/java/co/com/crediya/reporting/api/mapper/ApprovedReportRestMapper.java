package co.com.crediya.reporting.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import co.com.crediya.reporting.api.dto.ApprovedReportDTOResponse;
import co.com.crediya.reporting.model.approvedreport.ApprovedReport;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApprovedReportRestMapper {
  @Mapping(source = "entity", target = "approved")
  ApprovedReportDTOResponse toDTO(ApprovedReport entity);

  default ApprovedReportDTOResponse.Approved toApproved(ApprovedReport entity) {
    return new ApprovedReportDTOResponse.Approved(toCount(entity), toAmount(entity));
  }

  @Mapping(source = "totalCount", target = "value")
  @Mapping(source = "countLastUpdated", target = "updatedAt")
  ApprovedReportDTOResponse.Approved.Count toCount(ApprovedReport approvedReport);

  @Mapping(source = "totalAmount", target = "value")
  @Mapping(source = "amountLastUpdated", target = "updatedAt")
  ApprovedReportDTOResponse.Approved.Amount toAmount(ApprovedReport approvedReport);
}
