package com.marytts;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wyq
 * @date 2025/1/16
 * @desc
 */
public class StringTools {

    public static String dateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }
}
