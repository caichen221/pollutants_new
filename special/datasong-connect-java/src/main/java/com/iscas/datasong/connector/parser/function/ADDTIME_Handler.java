package com.iscas.datasong.connector.parser.function;

import cn.hutool.core.collection.CollectionUtil;
import com.iscas.datasong.connector.util.DateSafeUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ADDTIME(t,n) n 是一个时间表达式，时间 t 加上时间表达式 n
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/8/12 09:17
 * @since jdk11
 */
@SuppressWarnings({"JavadocDeclaration", "unused", "AlibabaClassNamingShouldBeCamel"})
public class ADDTIME_Handler implements FunctionHandler {

    @Override
    public void handle(Map<String, Object> data, Alias alias, Function func) {
        ExpressionList parameters = func.getParameters();
        if (parameters != null) {
            List<Expression> expressions = parameters.getExpressions();
            if (CollectionUtil.isNotEmpty(expressions)) {
                Object result = 0;
                Expression exp1 = expressions.get(0);
                Expression exp2 = expressions.get(1);
                Object first = getData(data, exp1);
                Object second = getData(data, exp2);
                if (first != null && second != null) {
                    String dateStr = first.toString();
                    try {
                        Date date = DateSafeUtils.parseStringToDate(dateStr);
                        String timeStr = second.toString();
                        if (!timeStr.contains(":")) {
                            try {
                                int s = Integer.parseInt(timeStr);
                            } catch (Exception e) {}
                        }
                        result = null;
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
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
