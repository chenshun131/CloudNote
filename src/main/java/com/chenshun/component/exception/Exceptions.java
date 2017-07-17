package com.chenshun.component.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * User: chenshun131 <p />
 * Time: 17/7/11 21:44  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Exceptions {

    public Exceptions() {
    }

    public static RuntimeException unchecked(Exception e) {
        return e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
    }

    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static boolean isCausedBy(Exception ex, Class... causeExceptionClasses) {
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            int var5 = causeExceptionClasses.length;
            for (int var4 = 0; var4 < var5; ++var4) {
                Class<? extends Exception> causeClass = causeExceptionClasses[var4];
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
        }
        return false;
    }

}
