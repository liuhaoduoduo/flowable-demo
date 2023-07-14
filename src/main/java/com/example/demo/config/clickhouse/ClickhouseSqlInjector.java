package com.example.demo.config.clickhouse;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@RequiredArgsConstructor
public class ClickhouseSqlInjector extends DefaultSqlInjector {

    private final ClickhouseProperties clickhouseProperties;

    @Override
    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        super.inspectInject(builderAssistant, mapperClass);
    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
         Stream.Builder<AbstractMethod> builder = Stream.<AbstractMethod>builder()
                .add(new Insert())
                .add(new ClickhouseDelete(clickhouseProperties))
                .add(new SelectObjs());
        if (tableInfo.havePK()) {
            builder.add(new ClickhouseSelectById());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
        }
        return builder.build().collect(toList());
    }
}
