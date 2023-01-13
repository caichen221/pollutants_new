package com.iscas.common.tools.core.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * LocalDate和LocalDateTime操作工具类
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/6 18:17
 * @since jdk11
 */
@SuppressWarnings("unused")
public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {
    }

    /**
     * 默认LocalDateTime类型的格式化串
     */
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认LocalDate类型的格式化串
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认LocalDateTime类型的格式化
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);

    /**
     * 默认LocalDate类型的格式化
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    /**
     * 将时间字符串转化为LocalDateTime, 格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param dataTimeStr 时间字符串，格式必须为：yyyy-MM-dd HH:mm:ss
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDateTime parseLocalDateTime(String dataTimeStr) {
        return LocalDateTime.parse(dataTimeStr, DATE_TIME_FORMATTER);
    }

    /**
     * 将时间字符串转化为LocalDateTime, 格式为自定义
     *
     * @param dateTimeStr 时间字符串
     * @param pattern     格式，例如：yyyy-MM-dd HH:mm:ss
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间字符串转化为LocalDateTime, 格式为自定义,带Locale
     *
     * @param dateTimeStr 时间字符串
     * @param pattern     格式，例如：yyyy-MM-dd HH:mm:ss
     * @param locale      locale
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern, Locale locale) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern, locale));
    }

    /**
     * 将时间字符串转化为LocalDate, 格式为：yyyy-MM-dd
     *
     * @param dateStr 时间字符串，格式必须为：yyyy-MM-dd
     * @return java.time.LocalDate
     * @date 2023/1/13
     * @since jdk1.8
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }

    /**
     * 将时间字符串转化为LocalDate, 格式自定义
     *
     * @param dateStr 时间字符串
     * @param pattern 格式，例如：yyyy-MM-dd
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间字符串转化为LocalDate, 格式自定义，带locale
     *
     * @param dateStr 时间字符串
     * @param pattern 格式，例如：yyyy-MM-dd
     * @param locale  locale
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern, Locale locale) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 计算两个时间之间相差的分钟
     *
     * @param start 开始
     * @param end   结束
     * @return long
     * @date 2023/1/13
     */
    public static long acquireMinutesBetween(LocalDateTime start, LocalDateTime end) {
        return start.until(end, ChronoUnit.MINUTES);
    }

    /**
     * 计算两个时间之间相差的毫秒数
     *
     * @param start 开始
     * @param end   结束
     * @return long
     * @date 2023/1/13
     */
    public static long acquireMillisBetween(LocalDateTime start, LocalDateTime end) {
        return start.until(end, ChronoUnit.MILLIS);
    }

    /**
     * 将时间毫秒数转为LocalDateTime,使用系统时区
     *
     * @param timestamp 时间毫秒数
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDateTime parseLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, OffsetDateTime.now().getOffset());
    }

    /**
     * 将时间毫秒数转为LocalDateTime
     *
     * @param timestamp  时间毫秒数
     * @param zoneOffset 时区
     * @return java.time.LocalDateTime
     * @date 2023/1/13
     */
    public static LocalDateTime parseLocalDateTime(long timestamp, ZoneOffset zoneOffset) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, zoneOffset);
    }

    /**
     * 将时间毫秒数转为LocalDate,使用系统时区
     *
     * @param timestamp 时间毫秒数
     * @return java.time.LocalDate
     * @date 2023/1/13
     */
    public static LocalDate parseLocalDate(long timestamp) {
        return parseLocalDateTime(timestamp).toLocalDate();
    }

    /**
     * 将时间毫秒数转为LocalDate
     *
     * @param timestamp  时间毫秒数
     * @param zoneOffset 时区
     * @return java.time.LocalDate
     * @date 2023/1/13
     */
    public static LocalDate parseLocalDate(long timestamp, ZoneOffset zoneOffset) {
        return parseLocalDateTime(timestamp, zoneOffset).toLocalDate();
    }

    /**
     * 时间转为字符串
     * @date 2023/1/13
     * @param localDateTime 时间
     * @return java.lang.String
     */
    public static String format(final LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * 时间转为字符串,自定义格式化串
     * @date 2023/1/13
     * @param localDateTime 时间
     * @param pattern 格式化串
     * @return java.lang.String
     */
    public static String format(final LocalDateTime localDateTime, final String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 时间转为字符串,自定义格式化串，带locale
     * @date 2023/1/13
     * @param localDateTime 时间
     * @param pattern 格式化串
     * @param locale locale
     * @return java.lang.String
     */
    public static String format(final LocalDateTime localDateTime, final String pattern, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return localDateTime.format(formatter);
    }

    /**
     * 时间转为字符串
     * @date 2023/1/13
     * @param localDate 时间
     * @return java.lang.String
     */
    public static String format(final LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * 时间转为字符串,自定义格式化串
     * @date 2023/1/13
     * @param localDate 时间
     * @param pattern 格式化串
     * @return java.lang.String
     */
    public static String format(final LocalDate localDate, final String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    /**
     * 时间转为字符串,自定义格式化串，带locale
     * @date 2023/1/13
     * @param localDate 时间
     * @param pattern 格式化串
     * @param locale locale
     * @return java.lang.String
     */
    public static String format(final LocalDate localDate, final String pattern, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return localDate.format(formatter);
    }

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime 时间
     * @param zoneOffset    时区偏移
     * @return long
     * @date 2022/6/9
     * @since jdk11
     */
    public static long toEpochMilli(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return localDateTime.toInstant(zoneOffset).toEpochMilli();
    }

    /**
     * LocalDateTime 转时间戳
     *
     * @param localDateTime 时间
     * @return long
     * @date 2022/6/9
     * @since jdk11
     */
    public static long toEpochMilli(LocalDateTime localDateTime) {
        return toEpochMilli(localDateTime, OffsetDateTime.now().getOffset());
    }

    /**
     * LocalDate 转时间戳
     *
     * @param localDate  时间
     * @param zoneOffset 时区偏移
     * @return long
     * @date 2022/6/9
     * @since jdk11
     */
    public static long toEpochMilli(LocalDate localDate, ZoneOffset zoneOffset) {
        return localDate.atStartOfDay(zoneOffset).toInstant().toEpochMilli();
    }

    /**
     * LocalDate 转时间戳
     *
     * @param localDate  时间
     * @return long
     * @date 2022/6/9
     * @since jdk11
     */
    public static long toEpochMilli(LocalDate localDate) {
        return toEpochMilli(localDate, OffsetDateTime.now().getOffset());
    }

}
