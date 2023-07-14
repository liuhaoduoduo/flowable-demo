package com.example.demo.config.clickhouse;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "bz.clickhouse")
public class ClickhouseProperties {
    /**
     * 地址
     */
    private String address;
    /**
     * 数据库
     */
    private String database;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 状态行
     */
    private String statusColumn = "effective";
}
