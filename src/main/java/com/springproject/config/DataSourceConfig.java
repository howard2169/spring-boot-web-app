package com.springproject.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DataSourceConfig {

    private static final String DS_URL_KEY = "datasource.url";
    private static final String DS_USERNAME_KEY = "datasource.username";
    private static final String DS_PASSWORD_KEY = "datasource.password";
    private static final String DRIVER_CLASS_NAME = "sys.connection.driver_class_name";
    private static final String MAX_POOL_SIZE_KEY = "sys.connection.max_pool_size";
    private static final String MIN_IDLE_SIZE_KEY = "sys.connection.min_idle_size";
    private static final String CONNECTION_TIMEOUT_KEY = "sys.connection.timeout";
    private static final Integer DEFAULT_MAX_POOL_SIZE = 100;
    private static final Integer DEFAULT_MIN_IDLE_SIZE = 25;
    private static final Long DEFAULT_CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30);

    private Properties datasourceProperties = new Properties();
    private Properties sysProperties = new Properties();
    private DataSource dataSource;

    public void setDatasourceProperties(Properties datasourceProperties) {
        this.datasourceProperties = datasourceProperties;
    }

    public void setSysProperties(Properties sysProperties) {
        this.sysProperties = sysProperties;
    }

    public DataSource build() {
        if (dataSource == null) {
            String url = Optional.ofNullable(datasourceProperties.getProperty(DS_URL_KEY)).orElseThrow(IllegalArgumentException::new);
            String userName = Optional.ofNullable(datasourceProperties.getProperty(DS_USERNAME_KEY)).orElseThrow(IllegalArgumentException::new);
            String password = Optional.ofNullable(datasourceProperties.getProperty(DS_PASSWORD_KEY)).orElseThrow(IllegalArgumentException::new);

            String driverClassName = Optional.ofNullable(sysProperties.getProperty(DRIVER_CLASS_NAME)).orElseThrow(IllegalArgumentException::new);
            Integer maxPoolSize = Optional.ofNullable(sysProperties.getProperty(MAX_POOL_SIZE_KEY)).map(Integer::valueOf).orElse(DEFAULT_MAX_POOL_SIZE);
            Integer minIdleSize = Optional.ofNullable(sysProperties.getProperty(MIN_IDLE_SIZE_KEY)).map(Integer::valueOf).orElse(DEFAULT_MIN_IDLE_SIZE);
            Long connectionTimeoutMs = Optional.ofNullable(sysProperties.getProperty(CONNECTION_TIMEOUT_KEY)).map(Long::valueOf).orElse(DEFAULT_CONNECTION_TIMEOUT);

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(driverClassName);
            config.setJdbcUrl(url);
            config.setUsername(userName);
            config.setPassword(password);
            config.setMaxLifetime(TimeUnit.MINUTES.toMillis(30)); // default
            config.setConnectionTimeout(connectionTimeoutMs);
            config.setMaximumPoolSize(maxPoolSize);
            config.setMinimumIdle(minIdleSize);
            config.setIdleTimeout(600000L);
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
