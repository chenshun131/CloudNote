package com.chenshun.test.concurrency.variable;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread Finish: %s : %s\n", Thread.currentThread().getId(), startDate.get());
    }

    public static void main(String[] args) throws InterruptedException {
        SafeTask safeTask = new SafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(safeTask);
            thread.start();
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
