package co.com.crediya.reporting.dynamodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import co.com.crediya.reporting.dynamodb.StatisticItem;
import co.com.crediya.reporting.model.approvedreport.ApprovedReport;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApprovedReportMapperStandard {
  default ApprovedReport toEntity(StatisticItem data) {
    throw new UnsupportedOperationException(
        "Cannot map a single StatisticItem to a full ApprovedReport.");
  }

  default StatisticItem toData(ApprovedReport entity) {
    throw new UnsupportedOperationException("Cannot map entity to StatisticItem.");
  }
}
