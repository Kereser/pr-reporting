package co.com.crediya.reporting.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "co.com.crediya.reporting.usecase",
    includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCaseImp$")},
    useDefaultFilters = false)
public class UseCasesConfig {}
