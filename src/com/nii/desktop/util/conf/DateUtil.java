package com.nii.desktop.util.conf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    // 默认使用系统当前时区
    private static final ZoneId ZONE = ZoneId.systemDefault();

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_NOFUII_FORMAT = "yyyyMMddHHmmss";

    private static final String REGEX = "\\:|\\-|\\s";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SDF_DETAIL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据传入的时间格式返回系统当前的时间
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
     * 将Date转换成LocalDateTime
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
     * 将Date转换成LocalDate
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
     * 将Date转换成LocalTime
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
     * 将LocalDate转换成Date
     *
     * @param localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }
    
    /**
     * 将LocalDate转换成DateTime
     *
     * @param localDate
     * @return date
     */
    public static String localDateToDateTimeStr(LocalDate localDate) {
        return SDF_DETAIL.format(localDateToDate(localDate));
    }

    /**
     * 将LocalDate转换成java.sql.Date
     *
     * @param localDate
     * @return date
     */
    public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return new java.sql.Date(Date.from(instant).getTime());
    }

    /**
     * 将java.sql.Date转换成java.util.Date
     *
     * @param localDate
     * @return date
     */
    public static Date sqlDateToUtilDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    /**
     * 将java.sql.Date转换成localDate
     *
     * @param localDate
     * @return date
     */
    public static LocalDate sqlDateToLocalDate(java.sql.Date date) {
        return dateToLocalDate(new Date(date.getTime()));
    }

    /**
     * 将LocalDateTime转换成Date
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将相应格式yyyy-MM-dd yyyy-MM-dd HH:mm:ss 时间字符串转换成Date
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

    // 获取本月25号的日期
    public static Date curMonth25Day() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-25";
        return SDF.parse(date);
    }

    // 获取上月26号的日期
    public static Date lastMonth26Day() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curMonth25Day());
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    // 获取3个月前的今天
    public static String last3MonthDateTimeStr() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -3);
        return SDF_DETAIL.format(cal.getTime());
    }

    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举 注:注意时间格式，避免cu选择不当的类型出现的异常
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
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举 注:注意时间格式，避免cu选择不当的类型出现的异常
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
     * 根据ChronoUnit枚举计算两个时间相加，日期类型对应枚举 注:注意时间格式，避免cu选择不当的类型出现的异常
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
     * 将time时间转换成毫秒时间戳
     *
     * @param time string
     * @return
     */
    public static long stringDateToMilli(String time) {
        return DateUtil.stringToDate(time, DATE_FORMAT_DEFAULT).toInstant().toEpochMilli();
    }

    /**
     * 将毫秒时间戳转换成Date
     *
     * @param time string
     * @return
     */
    public static Date timeMilliToDate(String time) {
        return Date.from(Instant.ofEpochMilli(Long.parseLong(time)));
    }

    public static void main(String[] args) throws ParseException {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.YEAR));
        System.out.println(cal.get(Calendar.MONTH) + 1);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(SDF.format(curMonth25Day()));
        System.out.println(SDF.format(lastMonth26Day()));
    }
}
