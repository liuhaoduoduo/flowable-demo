package com.example.demo.config.clickhouse;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

@RequiredArgsConstructor
public class ClickhouseDelete extends AbstractClickhouseSelect {


    private final ClickhouseProperties properties;

    // 获取状态行

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "<script>insert into %s %s select %s from %s %s</script>";
        // 构建需要查询的的字段，删除原理: 查询出来的现有最新的状态行数据，把状态改为 1取消行，然后进行插入
        StringBuilder selectColumnBuilder = new StringBuilder(tableInfo.getKeyInsertSqlColumn(false, false));
        StringBuilder insertColumnBuilder = new StringBuilder(tableInfo.getKeyInsertSqlColumn(false, false));
        for (TableFieldInfo fieldInfo : tableInfo.getFieldList()) {
            // 如果是状态行的话，设置为1
            if (StringUtils.equals(fieldInfo.getColumn(), properties.getStatusColumn())) {
                selectColumnBuilder.append("-1").append(",");
            } else {
                selectColumnBuilder.append(fieldInfo.getInsertSqlColumn());
            }
            insertColumnBuilder.append(fieldInfo.getInsertSqlColumn());
        }
        // 删除数据
        sql = String.format(sql, tableInfo.getTableName(),
                SqlScriptUtils.convertTrim(insertColumnBuilder.toString(), "(", ")", null, COMMA),
                SqlScriptUtils.convertTrim(selectColumnBuilder.toString(), null, null, null, COMMA),
                getSelectTableName(tableInfo, modelClass), sqlWhereEntityWrapper(true, tableInfo));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, Wrapper.class, SqlMethod.DELETE.getMethod(), sqlSource, NoKeyGenerator.INSTANCE, null, null);
    }
}
