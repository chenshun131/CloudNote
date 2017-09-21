package com.chenshun.test.concurrency.customer.threadfactory;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/21 16:31  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyTask implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
