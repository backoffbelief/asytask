package com.kael.asynctask.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 类 ExceptionUtil 的实现描述：TODO 类实现描述
 * 
 */
public class ExceptionUtil {

    public static String toString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();

        return sw.toString();
    }
}