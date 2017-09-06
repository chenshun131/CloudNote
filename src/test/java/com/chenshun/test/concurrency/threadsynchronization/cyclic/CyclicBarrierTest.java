package com.chenshun.test.concurrency.threadsynchronization.cyclic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * User: mew <p />
 * Time: 17/9/6 11:54  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class CyclicBarrierTest implements Runnable {

    private final CyclicBarrier cyclicBarrier;

    public CyclicBarrierTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Thread %s has start.\n", Thread.currentThread().getName());
            cyclicBarrier.await();
            System.out.printf("Thread %s has finished.\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        CyclicBarrierTest t = new CyclicBarrierTest(cyclicBarrier);

        for (int i = 0; i < 10; i++) {
            new Thread(t).start();
        }
    }

}
