package com.potato.config;

import lombok.Data;
import org.casbin.adapter.JDBCAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class CasbinAdapterConfig {

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  @Value("${spring.datasource.password}")
  private String datasourcePassword;

  @Value("${casbin.model-path}")
  private String modelPath;

  @Bean
  public JDBCAdapter jdbcAdapter() throws Exception {
    return new JDBCAdapter(driverClassName, datasourceUrl, datasourceUsername, datasourcePassword);
  }
}
