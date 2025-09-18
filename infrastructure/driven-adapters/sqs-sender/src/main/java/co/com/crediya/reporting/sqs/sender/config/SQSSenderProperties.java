package co.com.crediya.reporting.sqs.sender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapter.sqs")
public record SQSSenderProperties(
    String region, String endpoint, String approvedRelatedDailyReportQueue) {}
