package com.chenshun.test.concurrency.variable;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/29 19:20  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class UnSafeTask implements Runnable {

    private Date startDate;

    @Override
    public void run() {
        startDate = new Date();
        System.out.printf("Starting Thread: %s : %s\n", Thread.currentThread().getId(), startDate);
        try {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread Finish: %s : %s\n", Thread.currentThread().getId(), startDate);
    }

    public static void main(String[] args) throws InterruptedException {
        UnSafeTask unsafeTask = new UnSafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(unsafeTask);
            thread.start();
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
