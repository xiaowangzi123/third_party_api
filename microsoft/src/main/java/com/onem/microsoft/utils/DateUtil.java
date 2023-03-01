package com.microsoft.demo.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Class DateUtil.
 *
 * @author hewang
 */
public class DateUtil {

    /**
     * The Constant FORMAT_DATE_COMMON.
     */
    public static final String FORMAT_DATE_COMMON = "yyyy-MM-dd";

    /**
     * The Constant FORMAT_TIME_COMMON.
     */
    public static final String FORMAT_TIME_COMMON = "HH:mm:ss";

    /**
     * The Constant FORMAT_DATETIME_COMMON.
     */
    public static final String FORMAT_DATETIME_COMMON = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd-HH-mm-ss";


    /**
     * 比较两个时间，如果date1 < date2，返回-1； 如果date1 > date2，返回1； 如果date1 == date2，返回0
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare(Date date1, Date date2) {
        if (date1.getTime() < date2.getTime()) {
            return -1;
        } else if (date1.getTime() > date2.getTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Format date.
     *
     * @param date
     * @return the string
     */
    public static String formatDate(Date date) {
        return format(date, FORMAT_DATE_COMMON);
    }

    public static String getDateDefStr(Date date) {
        return format(date, FORMAT_DATETIME_DEFAULT);
    }


    /**
     * Format datetime.
     *
     * @param date
     * @return the string
     */
    public static String formatDatetime(Date date) {
        return format(date, FORMAT_DATETIME_COMMON);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm）
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Format.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Parses the date.
     *
     * @param dateString the date string
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date parseDate(String dateString) throws ParseException {
        return parse(dateString, FORMAT_DATE_COMMON);
    }

    /**
     * Parses the time.
     *
     * @param timeString the time string
     * @return the time
     * @throws ParseException the parse exception
     */
    public static Time parseTime(String timeString)
            throws ParseException {
        return new Time(parse(timeString, FORMAT_TIME_COMMON).getTime());
    }

    /**
     * Parses the datetime.
     *
     * @param datetimeString the datetime string
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date parseDatetime(String datetimeString)
            throws ParseException {
        return parse(datetimeString, FORMAT_DATETIME_COMMON);
    }

    /**
     * Parses the.
     *
     * @param dateString the date string
     * @param format     the format
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date parse(String dateString, String format)
            throws ParseException {
        return new SimpleDateFormat(format).parse(dateString);
    }

    public static void main(String argv[]) {
        System.out.println(getDateTime());
        System.out.println(getTime(new Date()));
        System.out.println(formatDate(new Date()));

        System.out.println(getDateDefStr(new Date()));

        Calendar calendar = new GregorianCalendar();
        System.out.println(Calendar.SATURDAY);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    }
}

