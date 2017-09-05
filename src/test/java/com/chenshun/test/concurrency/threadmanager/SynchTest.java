package com.chenshun.test.concurrency.threadmanager;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/4 17:37  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SynchTest implements Runnable {

    private Integer count = 0;

    @Override
    public void run() {
        synchronized (count) {
            count++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }
    }

    public Integer getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchTest run = new SynchTest();

        for (int i = 0; i < 20; i++) {
            new Thread(run).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.print("result : " + run.getCount());
    }

}
