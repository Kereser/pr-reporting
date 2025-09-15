package co.com.crediya.reporting.dynamodb;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import org.springframework.stereotype.Repository;

import co.com.crediya.reporting.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.reporting.dynamodb.mapper.ApprovedReportMapperStandard;
import co.com.crediya.reporting.model.approvedreport.ApprovedReport;
import co.com.crediya.reporting.model.approvedreport.CommonConstants;
import co.com.crediya.reporting.model.approvedreport.gateways.ApprovedReportRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

@Repository
@Slf4j
public class DynamoDBTemplateAdapter
    extends TemplateAdapterOperations<ApprovedReport, String, StatisticItem>
    implements ApprovedReportRepository {

  private final DynamoDbAsyncClient dynamoDbAsyncClient;

  private static final String LOG_UPDATE_STATISTIC_SUB =
      "Update statistic with key: {} and valueToAdd: {}";
  private static final String LOG_UPDATE_STATISTIC_ERROR =
      "Error while executing updateStatistic. Error {}";
  private static final String LOG_GET_STATISTIC_SUB = "Getting value for key: {}";
  private static final String LOG_GET_STATISTIC_ERROR =
      "Error while getting value for key: {}. Error: {}";
  private static final String LOG_GET_STATISTIC_SUCCESS = "Value obtained: {} for key: {}";

  private record StatisticResult(BigDecimal value, Instant lastUpdated) {}

  public DynamoDBTemplateAdapter(
      DynamoDbEnhancedAsyncClient connectionFactory,
      ApprovedReportMapperStandard mapper,
      DynamoDbAsyncClient dynamoDbAsyncClient) {
    super(
        connectionFactory,
        mapper::toEntity,
        mapper::toData,
        CommonConstants.StatisticItem.TABLE_NAME);
    this.dynamoDbAsyncClient = dynamoDbAsyncClient;
  }

  @Override
  public Mono<Void> incrementApprovedCount() {
    return updateStatistic(CommonConstants.StatisticItem.COUNT_KEY, BigDecimal.ONE);
  }

  @Override
  public Mono<Void> addApprovedAmount(BigDecimal amount) {
    return updateStatistic(CommonConstants.StatisticItem.AMOUNT_KEY, amount);
  }

  @Override
  public Mono<ApprovedReport> getApprovedReport() {
    Mono<StatisticResult> countMono = getStatisticValue(CommonConstants.StatisticItem.COUNT_KEY);
    Mono<StatisticResult> amountMono = getStatisticValue(CommonConstants.StatisticItem.AMOUNT_KEY);

    return Mono.zip(countMono, amountMono)
        .map(
            tuple ->
                ApprovedReport.builder()
                    .totalCount(tuple.getT1().value().longValue())
                    .countLastUpdated(tuple.getT1().lastUpdated())
                    .totalAmount(tuple.getT2().value())
                    .amountLastUpdated(tuple.getT2().lastUpdated())
                    .build());
  }

  private Mono<Void> updateStatistic(String key, BigDecimal valueToAdd) {
    UpdateItemRequest request =
        UpdateItemRequest.builder()
            .tableName(CommonConstants.StatisticItem.TABLE_NAME)
            .key(
                Map.of(
                    CommonConstants.StatisticItem.KEY_FIELD_NAME,
                    AttributeValue.builder().s(key).build()))
            .updateExpression(CommonConstants.StatisticItem.UPDATE_COUNTER_QUERY)
            .expressionAttributeNames(
                Map.of(
                    CommonConstants.StatisticItem.VALUE_PLACEHOLDER,
                        CommonConstants.StatisticItem.VALUE_FIELD_NAME,
                    CommonConstants.StatisticItem.LAST_UPDATED_PLACEHOLDER,
                        CommonConstants.StatisticItem.LAST_UPDATED_FIELD_NAME))
            .expressionAttributeValues(
                Map.of(
                    CommonConstants.StatisticItem.INCREMENT_VALUE,
                        AttributeValue.builder().n(valueToAdd.toPlainString()).build(),
                    CommonConstants.StatisticItem.TIMESTAMP_VALUE,
                        AttributeValue.builder().s(Instant.now().toString()).build()))
            .build();

    return Mono.fromFuture(dynamoDbAsyncClient.updateItem(request))
        .doOnSubscribe(sub -> log.info(LOG_UPDATE_STATISTIC_SUB, key, valueToAdd))
        .doOnError(err -> log.error(LOG_UPDATE_STATISTIC_ERROR, err.getMessage()))
        .then();
  }

  private Mono<StatisticResult> getStatisticValue(String key) {
    GetItemRequest request =
        GetItemRequest.builder()
            .tableName(CommonConstants.StatisticItem.TABLE_NAME)
            .key(
                Map.of(
                    CommonConstants.StatisticItem.KEY_FIELD_NAME,
                    AttributeValue.builder().s(key).build()))
            .build();

    return Mono.fromFuture(dynamoDbAsyncClient.getItem(request))
        .map(
            response -> {
              if (response.hasItem()) {
                Map<String, AttributeValue> item = response.item();

                BigDecimal value =
                    item.containsKey(CommonConstants.StatisticItem.VALUE_FIELD_NAME)
                        ? new BigDecimal(
                            item.get(CommonConstants.StatisticItem.VALUE_FIELD_NAME).n())
                        : BigDecimal.ZERO;
                Instant lastUpdate =
                    item.containsKey(CommonConstants.StatisticItem.LAST_UPDATED_FIELD_NAME)
                        ? Instant.parse(
                            item.get(CommonConstants.StatisticItem.LAST_UPDATED_FIELD_NAME).s())
                        : null;

                return new StatisticResult(value, lastUpdate);
              }

              return new StatisticResult(BigDecimal.ZERO, null);
            })
        .doOnSubscribe(sub -> log.info(LOG_GET_STATISTIC_SUB, key))
        .doOnSuccess(res -> log.info(LOG_GET_STATISTIC_SUCCESS, key, res.value()))
        .doOnError(err -> log.error(LOG_GET_STATISTIC_ERROR, key, err.getMessage()));
  }
}
