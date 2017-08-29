package com.chenshun.test.concurrency.variable;

import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/8/29 19:28  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SafeTask implements Runnable {

    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
        @Override
        protected Date initialValue() {
            return new Date();
        }
    };

    @Override
    public void run() {
        System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate.get());
    }

}
