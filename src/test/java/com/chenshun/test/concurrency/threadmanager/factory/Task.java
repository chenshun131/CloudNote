package com.chenshun.test.concurrency.threadmanager.factory;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/30 19:11  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
