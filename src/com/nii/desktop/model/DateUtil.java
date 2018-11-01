package com.nii.desktop.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DateUtil {

    // Ĭ��ʹ��ϵͳ��ǰʱ��
    private static final ZoneId ZONE = ZoneId.systemDefault();

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_NOFUII_FORMAT = "yyyyMMddHHmmss";

    private static final String REGEX = "\\:|\\-|\\s";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * ���ݴ����ʱ���ʽ����ϵͳ��ǰ��ʱ��
     *
     * @param format string
     * @return
     */
    public static String timeByFormat(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return now.format(dateTimeFormatter);
    }

    /**
     * ��Dateת����LocalDateTime
     *
     * @param d date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    /**
     * ��Dateת����LocalDate
     *
     * @param d date
     * @return
     */
    public static LocalDate dateToLocalDate(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    /**
     * ��Dateת����LocalTime
     *
     * @param d date
     * @return
     */
    public static LocalTime dateToLocalTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * ��LocalDateת����Date
     *
     * @param localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * ��LocalDateת����java.sql.Date
     *
     * @param localDate
     * @return date
     */
    public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return new java.sql.Date(Date.from(instant).getTime());
    }

    /**
     * ��java.sql.Dateת����java.util.Date
     *
     * @param localDate
     * @return date
     */
    public static Date sqlDateToUtilDate(java.sql.Date date) {
        return new Date(date.getTime());
    }
    
    /**
     * ��java.sql.Dateת����localDate
     *
     * @param localDate
     * @return date
     */
    public static LocalDate sqlDateToLocalDate(java.sql.Date date) {
        return dateToLocalDate(new Date(date.getTime()));
    }

    /**
     * ��LocalDateTimeת����Date
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * ����Ӧ��ʽyyyy-MM-dd yyyy-MM-dd HH:mm:ss ʱ���ַ���ת����Date
     *
     * @param time   string
     * @param format string
     * @return date
     */
    public static Date stringToDate(String time, String format) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
        if (DATE_FORMAT_DEFAULT.equals(format)) {
            return DateUtil.localDateTimeToDate(LocalDateTime.parse(time, f));
        } else if (DATE_FORMAT.equals(format)) {
            return DateUtil.localDateToDate(LocalDate.parse(time, f));
        }
        return null;
    }

    /**
     * ����ChronoUnitö�ټ�������ʱ���������Ͷ�Ӧö�� ע:ע��ʱ���ʽ������cuѡ�񲻵������ͳ��ֵ��쳣
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 date
     * @return
     */
    public static long chronoUnitBetweenByDate(ChronoUnit cu, Date d1, Date d2) {
        return cu.between(DateUtil.dateToLocalDateTime(d1), DateUtil.dateToLocalDateTime(d2));
    }

    /**
     * ����ChronoUnitö�ټ�������ʱ���������Ͷ�Ӧö�� ע:ע��ʱ���ʽ������cuѡ�񲻵������ͳ��ֵ��쳣
     *
     * @param cu chronoUnit.enum.key
     * @param s1 string
     * @param s2 string
     * @return
     */
    public static Long chronoUnitBetweenByString(ChronoUnit cu, String s1, String s2, String dateFormat) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(dateFormat);
        if (DATE_FORMAT_DEFAULT.equals(dateFormat)) {
            LocalDateTime l1 = DateUtil.dateToLocalDateTime(DateUtil.stringToDate(s1, dateFormat));
            LocalDateTime l2 = DateUtil.dateToLocalDateTime(DateUtil.stringToDate(s2, dateFormat));
            return cu.between(l1, l2);
        }
        if (DATE_FORMAT.equals(dateFormat)) {
            LocalDate l1 = DateUtil.dateToLocalDate(DateUtil.stringToDate(s1, dateFormat));
            LocalDate l2 = DateUtil.dateToLocalDate(DateUtil.stringToDate(s2, dateFormat));
            return cu.between(l1, l2);
        }
        if (TIME_NOFUII_FORMAT.equals(dateFormat)) {
            LocalDateTime l1 = LocalDateTime.parse(s1.replaceAll(REGEX, ""), f);
            LocalDateTime l2 = LocalDateTime.parse(s2.replaceAll(REGEX, ""), f);
            return cu.between(l1, l2);
        }
        return null;
    }

    /**
     * ����ChronoUnitö�ټ�������ʱ����ӣ��������Ͷ�Ӧö�� ע:ע��ʱ���ʽ������cuѡ�񲻵������ͳ��ֵ��쳣
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 long
     * @return
     */
    public static Date chronoUnitPlusByDate(ChronoUnit cu, Date d1, long d2) {
        return DateUtil.localDateTimeToDate(cu.addTo(DateUtil.dateToLocalDateTime(d1), d2));
    }

    /**
     * ��timeʱ��ת���ɺ���ʱ���
     *
     * @param time string
     * @return
     */
    public static long stringDateToMilli(String time) {
        return DateUtil.stringToDate(time, DATE_FORMAT_DEFAULT).toInstant().toEpochMilli();
    }

    /**
     * ������ʱ���ת����Date
     *
     * @param time string
     * @return
     */
    public static Date timeMilliToDate(String time) {
        return Date.from(Instant.ofEpochMilli(Long.parseLong(time)));
    }

    public static void main(String[] args) {
        System.out.println(dateToLocalDate(new Date()));
    }
}