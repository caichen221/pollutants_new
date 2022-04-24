package com.iscas.workflow.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/1/27 13:50
 * @since jdk1.8
 */
@Data
@ConfigurationProperties(prefix = "camunda.bpm.datasource")
public class DatasourceProps extends DataSourceProperties {
}
