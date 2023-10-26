package com.example.speechmatics.utils;


import cn.hutool.core.util.StrUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeConvertUtil {
    private static final Pattern pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})");

    public static void main(String[] args) {
        System.out.println(timecodeConvert(88560));
    }

    //0:00:52.00
    public static String timeConvert(int time) {
        Date now = new Date(time - (3600000 * 8)); // 减去系统的初始8个小时
        // 指定格式化格
        //SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(now);
    }

    /**
     * HH:mm:ss.SSS格式字符串转成Long类型时间戳
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Long getTimeStamp(String time) {
        if (StrUtil.isBlank(time)){
            return 0L;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            if (time.length() == 11) {
                date = sdf.parse("1970-01-01 " + time + "0");
            } else {
                date = sdf.parse("1970-01-01 " + time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return date.getTime();
        }
        return 0L;
    }

    public static Integer dateParseRegExp(String time) {
        Matcher matcher = pattern.matcher(time);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * 3600000
                    + Integer.parseInt(matcher.group(2)) * 60000
                    + Integer.parseInt(matcher.group(3)) * 1000
                    + Integer.parseInt(matcher.group(4));
        } else {
            throw new IllegalArgumentException("Invalid format " + time);
        }
    }


    /**
     * 时间码转成srt时间
     */
    public static String timecodeConvert(int timecode){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return formatter.format(new Date(timecode)).replace(".",",");
    }
}