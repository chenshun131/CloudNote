package com.chenshun.test.concurrency.executor.executorframework;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/7 10:54  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    private Date initDate;

    private String name;

    public Task(String name) {
        this.initDate = new Date();
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Task %s: Created on: %s\n", Thread.currentThread().getName(), name, initDate);
        System.out.printf("%s: Task %s: Started on: %s\n", Thread.currentThread().getName(), name, new Date());

        try {
            Long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Task %s:Doing a Task during %d seconds\n", Thread.currentThread().getName(), name,
                    duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Task %s: Finished on: %s\n", Thread.currentThread().getName(), name, new Date());
    }

}
