package com.iscas.datasong.connector.parser.function;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Function;

import java.util.Map;

/**
 * PERIOD_ADD(period, number) 	为 年-月 组合日期添加一个时段
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/8/17 9:11
 * @since jdk11
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel", "unused"})
public class PERIOD_ADD_Handler implements FunctionHandler {
    @Override
    public void handle(Map<String, Object> data, Alias alias, Function func) {
        throw new UnsupportedOperationException("暂不支持PERIOD_ADD方法");
    }
}
