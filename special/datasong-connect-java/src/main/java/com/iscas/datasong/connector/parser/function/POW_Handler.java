package com.iscas.datasong.connector.parser.function;

import cn.hutool.core.collection.CollectionUtil;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import java.util.List;
import java.util.Map;

/**
 * POW(x,y)  返回 x 的 y 次方　
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/8/11 16:18
 * @since jdk11
 */
@SuppressWarnings({"JavadocDeclaration", "unused", "AlibabaClassNamingShouldBeCamel"})
public class POW_Handler implements FunctionHandler {
    @Override
    public void handle(Map<String, Object> data, Alias alias, Function func) {
        ExpressionList parameters = func.getParameters();
        if (parameters != null) {
            List<Expression> expressions = parameters.getExpressions();
            if (CollectionUtil.isNotEmpty(expressions)) {
                Object result = null;
                Expression exp1 = expressions.get(0);
                Expression exp2 = expressions.get(1);
                Object first = getData(data, exp1);
                Object second = getData(data, exp2);
                if (first != null && second != null) {
                    Double x = null;
                    try {
                        x = Double.parseDouble(first.toString());
                    } catch (Exception ignored) {
                    }
                    Double y = null;
                    try {
                        y = Double.parseDouble(second.toString());
                    } catch (Exception ignored) {
                    }
                    if (x != null && y != null) {
                        result = Math.pow(x, y);
                    }
                }
                if (alias != null) {
                    data.put(alias.getName(), result);
                } else {
                    data.put(func.toString(), result);
                }
            }
        }
    }

}
