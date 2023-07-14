package com.example.demo.config.clickhouse;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;


public class ClickhouseSelectById extends AbstractClickhouseSelect {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String defaultSql = "SELECT %s FROM %s WHERE %s=#{%s}";
        SqlSource sqlSource = new RawSqlSource(configuration, String.format(defaultSql,
                sqlSelectColumns(tableInfo, false),
                getSelectTableName(tableInfo, modelClass), tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty()), Object.class);
        return this.addSelectMappedStatementForTable(mapperClass, SqlMethod.SELECT_BY_ID.getMethod(), sqlSource, tableInfo);
    }
}
