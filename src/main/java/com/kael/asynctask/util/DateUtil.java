package com.kael.asynctask.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类 DateUtil 的实现描述：TODO 类实现描述
 * 
 */
public class DateUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String now() {
        return format.format(new Date());
    }

    public static String format(Date date) {
        return format.format(date);
    }

    public static String fromat(String format, Date date) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static Date parse(String source) {
        try {
            return format.parse(source);
        } catch (Exception e) {
            LogHelper.exception(e);
        }

        return null;
    }
}