package com.chenshun.test.concurrency.customer.threadfactory;

import java.util.concurrent.ThreadFactory;

/**
 * User: mew <p />
 * Time: 17/9/21 16:28  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyThreadFactory implements ThreadFactory {

    private int counter;

    private String prefix;

    public MyThreadFactory(String prefix) {
        this.prefix = prefix;
        counter = 1;
    }

    @Override
    public Thread newThread(Runnable r) {
        MyThread myThread = new MyThread(r, prefix + "-" + counter);
        counter++;
        return myThread;
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadFactory myFactory = new MyThreadFactory("MyThreadFactory");
        MyTask task = new MyTask();
        Thread thread = myFactory.newThread(task);

        thread.start();
        thread.join();
        System.out.printf("Main: Thread information.\n");
        System.out.printf("%s\n", thread);
        System.out.printf("Main: End of example.\n");
    }

}
