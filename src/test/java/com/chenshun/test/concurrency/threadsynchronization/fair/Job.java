package com.chenshun.test.concurrency.threadsynchronization.fair;

/**
 * User: mew <p />
 * Time: 17/9/1 08:45  <p />
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
        System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
        printQueue.printJob(new Object());
        System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
    }

}
