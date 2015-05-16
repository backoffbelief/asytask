package com.kael.asynctask.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private static AtomicInteger    _count  = new AtomicInteger(0);
    private static Date       _time   = new Date();
    private static DateFormat _format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public synchronized static String gen() {

        Date now = new Date();
        if (now.getTime() != _time.getTime()) {
            _time = now;
            _count.set(0);
        }
        return String.format("%s_%010d", _format.format(_time), _count.getAndIncrement());
    }

    public static void main(String args[]) {

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true)
                        System.out.println(IDGenerator.gen());
                }
            });
            thread.start();
        }
    }
}