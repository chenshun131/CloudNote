package com.chenshun.test.concurrency.customer.forkandjoin;

import java.util.concurrent.RecursiveTask;

/**
 * User: chenshun131 <p />
 * Time: 17/9/22 23:06  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyRecursiveTask extends RecursiveTask<Integer> {

    private int[] array;

    private int start, end;

    public MyRecursiveTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        Integer ret;
        MyWorkerThread thread = (MyWorkerThread) Thread.currentThread();
        thread.addTask();
        return null;
    }

}
