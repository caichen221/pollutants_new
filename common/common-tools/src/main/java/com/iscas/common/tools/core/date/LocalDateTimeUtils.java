package com.iscas.common.tools.core.date;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2022/4/6 18:17
 * @since jdk11
 */
public class LocalDateTimeUtils {

    private static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_DATETIME);


    public static LocalDateTime parseLocalDateTime(final String dataTime) {
        return LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern(DATE_FORMAT_DATETIME));
    }


    public static LocalDateTime parseLocalDateTime(final String dataTime, final String dateTimeFormatter) {
        return LocalDateTime.parse(dataTime, DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    public static long acquireMinutesBetween(final LocalDateTime start, final LocalDateTime end) {
        return start.until(end, ChronoUnit.MINUTES);
    }

    public static long acquireMillisBetween(final LocalDateTime start, final LocalDateTime end) {
        return start.until(end, ChronoUnit.MILLIS);
    }

    public static LocalDateTime formatLocalDateTimeFromTimestamp(final Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime formatLocalDateTimeFromTimestampBySystemTimezone(final Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, OffsetDateTime.now().getOffset());
    }

    public static String localDateTimeToString(final LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    public static String localDateTimeToString(final LocalDateTime localDateTime, final String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }
}
