package com.chenshun.test.concurrency.threadsynchronization.multiSema;

/**
 * User: mew <p />
 * Time: 17/9/5 16:46  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Job implements Runnable {

    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s: going to print a job\n", Thread.currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }

}
