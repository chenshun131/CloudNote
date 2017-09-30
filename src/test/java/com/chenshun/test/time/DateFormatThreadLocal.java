package com.chenshun.test.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: chenshun131 <p />
 * Time: 17/9/26 23:40  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static final Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }

}