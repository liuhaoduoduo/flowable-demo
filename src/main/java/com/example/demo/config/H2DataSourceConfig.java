package com.example.demo.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
@Data
@ConfigurationProperties(prefix = "bz.h2")
public class H2DataSourceConfig {
    private String url;
    private String username;
    private String password;
    @Primary
    @Bean(name = "H2")
    public JdbcDataSource careteH2DataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl(url);
        jdbcDataSource.setUser(username);
        jdbcDataSource.setPassword(password);
        return jdbcDataSource;
    }

}
