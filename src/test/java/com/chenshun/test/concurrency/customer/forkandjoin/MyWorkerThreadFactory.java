package com.chenshun.test.concurrency.customer.forkandjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * User: chenshun131 <p />
 * Time: 17/9/22 23:05  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new MyWorkerThread(pool);
    }

}
