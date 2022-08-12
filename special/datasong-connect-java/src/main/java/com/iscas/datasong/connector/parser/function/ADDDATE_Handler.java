package com.iscas.datasong.connector.parser.function;

import cn.hutool.core.collection.CollectionUtil;
import com.iscas.datasong.connector.util.DateSafeUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ADDDATE(d,n) 计算起始日期 d 加上 n 天的日期
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/8/12 09:17
 * @since jdk11
 */
@SuppressWarnings({"JavadocDeclaration", "unused", "AlibabaClassNamingShouldBeCamel"})
public class ADDDATE_Handler implements FunctionHandler {

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
                        long time = 0L;
                        if (exp2 instanceof IntervalExpression) {
                            time = getIntervalMs(date, (IntervalExpression) exp2);
                        } else {
                            try {
                                double v = Double.parseDouble(second.toString());
                                time = date.getTime() + Math.round(v) * 24 * 3600 * 1000L;
                            } catch (Exception ignored) {}
                        }
                        result =  DateSafeUtils.format(new Date(time));
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
