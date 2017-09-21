package com.chenshun.test.concurrency.customer.schedule;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/21 17:03  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    @Override
    public void run() {
        System.out.printf("Task: Begin.\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Task: End.\n");
    }

}
