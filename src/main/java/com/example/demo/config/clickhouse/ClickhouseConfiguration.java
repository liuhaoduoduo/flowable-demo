package com.example.demo.config.clickhouse;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(ClickhouseProperties.class)
public class ClickhouseConfiguration {
    @Resource
    private ClickhouseProperties clickhouseProperties;

    @Bean
    public ClickhouseSqlInjector clickhouseSqlInjector() {
        return new ClickhouseSqlInjector(clickhouseProperties);
    }

    @Bean
    public ClickHouseDataSource clickHouseDataSource() {
        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setUser(clickhouseProperties.getUsername());
        properties.setPassword(clickhouseProperties.getPassword());
        properties.setDatabase(clickhouseProperties.getDatabase());
        properties.setCompress(true);
        properties.setUseServerTimeZone(false);
        properties.setUseServerTimeZoneForDates(true);
        properties.setUseTimeZone("Asia/Shanghai");
        properties.setSocketTimeout(300000);
        return new ClickHouseDataSource(clickhouseProperties.getAddress(), properties);
    }
}
