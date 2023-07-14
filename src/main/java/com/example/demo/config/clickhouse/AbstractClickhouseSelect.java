package com.example.demo.config.clickhouse;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.example.demo.config.clickhouse.anno.ClickhouseView;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


public abstract class AbstractClickhouseSelect extends AbstractMethod {

    public String getSelectTableName(TableInfo tableInfo, Class<?> modelClass) {
        String tableName = tableInfo.getTableName();
        ClickhouseView clickhouseView = modelClass.getAnnotation(ClickhouseView.class);
        if (Objects.nonNull(clickhouseView)) {
            if (StringUtils.isBlank(clickhouseView.value())) {
                tableName = "view_" + tableName;
            } else {
                tableName = clickhouseView.value();
            }
        }
        return tableName;
    }
}
