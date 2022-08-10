package com.iscas.datasong.connector.parser.function;

import cn.hutool.core.util.ArrayUtil;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.schema.Column;

import java.util.Map;

/**
 * https://www.runoob.com/mysql/mysql-functions.html
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/7/28 10:40
 * @since jdk11
 */
public interface FunctionHandler {
    /**
     * 处理函数
     *
     * @param data  数据
     * @param alias 别名
     * @param func  function
     */
    void handle(Map<String, Object> data, Alias alias, Function func);

    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    default boolean isStr(String columnName) {
        return (columnName.startsWith("\"") && columnName.endsWith("\"")) ||
                (columnName.startsWith("'") && columnName.endsWith("'"));
    }

    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    default Object getData(Map<String, Object> data, Expression expression) {
        if (expression instanceof Column) {
            Column column = (Column) expression;
            String columnName = column.getColumnName();
            if (isStr(columnName)) {
                columnName = columnName.substring(1, columnName.length() - 1);
                return columnName;
            } else {
                if (data.containsKey(columnName)) {
                    return data.get(columnName);
                } else {
                    return null;
                }
            }
        } else if (expression instanceof StringValue) {
            return ((StringValue) expression).getValue();
        } else if (expression instanceof LongValue) {
            return ((LongValue) expression).getValue();
        } else if (expression instanceof DoubleValue) {
            return ((DoubleValue) expression).getValue();
        } else if (expression instanceof SignedExpression) {
            SignedExpression signedExpression = (SignedExpression) expression;
            char sign = signedExpression.getSign();
            if (sign == '-') {
                Object data1 = getData(data, ((SignedExpression) expression).getExpression());
                if (data1 instanceof Long) {
                    Long d = (Long) data1;
                    return -d;
                } else if (data1 instanceof Double) {
                    Double d = (Double) data1;
                    return -d;
                } else {
                    throw new RuntimeException(String.format("不支持的Expression类型:[%s]", expression.getClass().getName()));
                }
            } else {
                throw new RuntimeException(String.format("不支持的Expression类型:[%s]", expression.getClass().getName()));
            }
        } else {
            throw new RuntimeException(String.format("不支持的Expression类型:[%s]", expression.getClass().getName()));
        }
    }

    default String escape(String str) {
        String[] needStrs = new String[]{"$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|", "\\"};
        if (ArrayUtil.contains(needStrs, str)) {
            return "\\" + str;
        }
        return str;
    }
}
