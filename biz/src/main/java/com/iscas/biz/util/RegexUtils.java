package com.iscas.biz.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/21 14:12
 * @since jdk1.8
 */
public class RegexUtils {

    public final static String START_NUMBER = "\\d+(\\.\\d+)?";

    public final static Pattern p = Pattern.compile(START_NUMBER);

    public static String getStartNumber(String content) {
        if (StringUtils.isNotBlank(content)) {
            Matcher m = p.matcher(content);
            if (m.find()) return m.group(0);
        }
        return content;
    }
}
