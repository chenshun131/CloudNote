package com.chenshun.test.concurrency.threadsynchronization.fair;

import java.util.concurrent.locks.ReentrantLock;

/**
 * User: mew <p />
 * Time: 17/9/1 08:39  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class PrintQueue {

    private final ReentrantLock queueLock = new ReentrantLock(true);

    public void printJob(Object document) {
        queueLock.lock();

        try {
            Long duration = (long) (Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + ":PrintQueue: Printing a Job during [" +
                    duration + "]milliseconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
        queueLock.lock();
        try {
            Long duration = (long) (Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + ":PrintQueue: Printing a Job during [" +
                    duration + "]milliseconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrintQueue printQueue = new PrintQueue();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread " + i);
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
            Thread.sleep(100);
        }
    }

}
