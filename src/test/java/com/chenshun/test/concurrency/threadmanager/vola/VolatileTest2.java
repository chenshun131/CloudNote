package com.chenshun.test.concurrency.threadmanager.vola;

import java.util.concurrent.TimeUnit;

/**
 * User: chenshun131 <p />
 * Time: 17/9/2 00:10  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class VolatileTest2 {

    private static  boolean stopThread;

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (!stopThread) {
                    i++;
                }
            }
        });
        th.start();
        TimeUnit.SECONDS.sleep(2);
        stopThread = true;
    }


}
