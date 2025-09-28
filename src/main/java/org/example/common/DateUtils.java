package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author huaiziqing
 */
public class DateUtils {

    /**
     * 标准日期时间格式（ISO_LOCAL_DATE_TIME）
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将LocalDateTime格式化为字符串
     * @param dateTime 日期时间
     * @param pattern 格式模板
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 使用默认格式格式化日期时间
     * @param dateTime 日期时间
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_PATTERN);
    }

    /**
     * 计算两个日期时间之间的天数差
     * @param start 开始日期时间
     * @param end 结束日期时间
     * @return 天数差
     */
    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return java.time.Duration.between(start, end).toDays();
    }

    /**
     * 判断是否是同一天
     * @param dateTime1 第一个日期时间
     * @param dateTime2 第二个日期时间
     * @return 如果是同一天返回true，否则返回false
     */
    public static boolean isSameDay(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return false;
        }
        return dateTime1.toLocalDate().isEqual(dateTime2.toLocalDate());
    }
}
