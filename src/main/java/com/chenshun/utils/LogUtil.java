package com.chenshun.utils;

import com.chenshun.utils.date.DateUtil;
import com.chenshun.utils.validate.Func;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * User: mew <p />
 * Time: 17/7/11 16:08  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class LogUtil {

    private static class Holder {

        private static Logger log = LoggerFactory.getLogger(LogUtil.class);
    }

    /**
     * 当通过 Constants.setLogFactory(...) 或者
     * LogManager.me().setDefaultLogFacotyr(...) 指定默认日志工厂以后，重置一下内部 Log
     * 对象，以便使内部日志实现与系统保持一致
     */
    public static void synchronizeLog() {
        Holder.log = LoggerFactory.getLogger(LogUtil.class);
    }

    /**
     * Do nothing.
     */
    public static void logNothing(Throwable t) {
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void println(String message, Object... values) {
        System.out.println(Func.format(message, values));
    }

    public static void println(String message, Map<?, ?> map) {
        System.out.println(Func.format(message, map));
    }

    public static void report(String message) {
        System.out.println("/nStudyApp report ------------- " + DateUtil.getTime() + " -----------------------------");
        System.out.println("msg	: " + message);
        System.out.println("---------------------------------------------------------------------------------");
    }

    public static void debug(String message) {
        Holder.log.debug(message);
    }

    public static void debug(String message, Throwable t) {
        Holder.log.debug(message, t);
    }

    public static void info(String message) {
        Holder.log.info(message);
    }

    public static void info(String message, Throwable t) {
        Holder.log.info(message, t);
    }

    public static void warn(String message) {
        Holder.log.warn(message);
    }

    public static void warn(String message, Throwable t) {
        Holder.log.warn(message, t);
    }

    public static void error(String message) {
        Holder.log.error(message);
    }

    public static void error(String message, Throwable t) {
        Holder.log.error(message, t);
    }

    public static boolean isDebugEnabled() {
        return Holder.log.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return Holder.log.isInfoEnabled();
    }

}
