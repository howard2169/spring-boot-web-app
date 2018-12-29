package com.springproject.config;

import com.springproject.util.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Properties;

@Import(WebMvcConfig.class)
@Configuration
public class AppContextConfig {

    @Bean
    public DataSource dataSource() {
        Properties datasourceProperties = PropertiesUtil.load("datasource.properties");
        Properties sysProperties = PropertiesUtil.load("sys.properties");

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDatasourceProperties(datasourceProperties);
        dataSourceConfig.setSysProperties(sysProperties);
        return dataSourceConfig.build();
    }

    @Bean
    public JPAConfiguration jpaConfiguration() {
        JPAConfiguration configuration = new JPAConfiguration();
        configuration.setPackagesToScan("com.springproject.to");
        return configuration;
    }
}

