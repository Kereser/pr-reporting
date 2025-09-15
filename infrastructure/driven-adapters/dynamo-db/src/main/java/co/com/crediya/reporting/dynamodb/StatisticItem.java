package co.com.crediya.reporting.dynamodb;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class StatisticItem {
  private String statisticId;
  private BigDecimal value;
  private Instant lastUpdated;

  public StatisticItem() {}

  @DynamoDbPartitionKey
  @DynamoDbAttribute("statisticId")
  public String getStatisticId() {
    return statisticId;
  }

  public void setStatisticId(String statisticId) {
    this.statisticId = statisticId;
  }

  @DynamoDbAttribute("value")
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @DynamoDbAttribute("lastUpdated")
  public Instant getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Instant lastUpdated) {
    this.lastUpdated = lastUpdated;
  }
}
