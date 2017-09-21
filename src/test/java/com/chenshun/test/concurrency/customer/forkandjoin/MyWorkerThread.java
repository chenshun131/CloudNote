package com.chenshun.test.concurrency.customer.forkandjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * User: mew <p />
 * Time: 17/9/21 17:45  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyWorkerThread extends ForkJoinWorkerThread {

    private static ThreadLocal<Integer> taskCounter = new ThreadLocal<>();

    protected MyWorkerThread(ForkJoinPool pool) {
        super(pool);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.printf("MyWorkerThread %d: Initializing task counter.\n", getId());
        taskCounter.set(0);
    }

    @Override
    protected void onTermination(Throwable exception) {
        System.out.printf("MyWorkerThread %d: %d\n", getId(), taskCounter.get());
        super.onTermination(exception);
    }

    private void addTask() {
        int counter = taskCounter.get().intValue();
        counter++;
        taskCounter.set(counter);
    }

}
