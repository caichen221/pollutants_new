package com.iscas.datasong.connector.parser;

import net.sf.jsqlparser.JSQLParserException;

import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/7 20:56
 * @since jdk1.8
 */
public class SqlParser {
    public static void getSelect(String sql) throws JSQLParserException {
        List<String> items = SqlParserUtils.selectItems(sql);
        List<String> strings = SqlParserUtils.selectTable(sql);
    }
}
