package com.chenshun.test.concurrency.threadmanager.groupexception;

import java.util.Random;

/**
 * User: mew <p />
 * Time: 17/8/30 09:59  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    @Override
    public void run() {
        int result;
        Random random = new Random(Thread.currentThread().getId());
        while (true) {
            result = 1000 / ((int) (random.nextDouble() * 1000));
            System.out.printf("%s: %d\n", Thread.currentThread().getId(), result);
            if(Thread.currentThread().isInterrupted()){
                System.out.printf("%d : Interrupt\n", Thread.currentThread().getId());
                return;
            }
        }
    }

}
