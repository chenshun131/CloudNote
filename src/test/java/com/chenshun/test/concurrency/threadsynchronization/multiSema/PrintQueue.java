package com.chenshun.test.concurrency.threadsynchronization.multiSema;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: mew <p />
 * Time: 17/9/5 16:37  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class PrintQueue {

    private final Semaphore semaphore;

    private boolean freeprinters[];

    private ReentrantLock lockPrinters;

    public PrintQueue() {
        semaphore = new Semaphore(3);
        freeprinters = new boolean[3];
        for (int i = 0; i < freeprinters.length; i++) {
            freeprinters[i] = true;
        }
        lockPrinters = new ReentrantLock();
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            int assignPointesr = getPrinter();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",
                    Thread.currentThread().getName(), duration);
            TimeUnit.SECONDS.sleep(duration);
            freeprinters[assignPointesr] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int getPrinter() {
        int ret = 1;
        try {
            lockPrinters.lock();
            for (int i = 0; i < freeprinters.length; i++) {
                if (freeprinters[i]) {
                    ret = i;
                    freeprinters[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread" + i);
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }

}
