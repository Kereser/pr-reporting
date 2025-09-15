package co.com.crediya.reporting.dynamodb.helper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.crediya.reporting.dynamodb.ModelEntity;
import co.com.crediya.reporting.dynamodb.mapper.ApprovedReportMapperStandard;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

class TemplateAdapterOperationsTest {

  @Mock private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

  @Mock private ApprovedReportMapperStandard mapper;

  @Mock private DynamoDbAsyncTable<ModelEntity> customerTable;

  private ModelEntity modelEntity;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ModelEntity.class)))
        .thenReturn(customerTable);

    modelEntity = new ModelEntity();
    modelEntity.setId("id");
    modelEntity.setAtr1("atr1");
  }

  @Test
  void modelEntityPropertiesMustNotBeNull() {
    ModelEntity modelEntityUnderTest = new ModelEntity("id", "atr1");

    assertNotNull(modelEntityUnderTest.getId());
    assertNotNull(modelEntityUnderTest.getAtr1());
  }
}
