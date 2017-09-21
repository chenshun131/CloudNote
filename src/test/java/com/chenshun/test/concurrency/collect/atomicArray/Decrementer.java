package com.chenshun.test.concurrency.collect.atomicArray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * User: mew <p />
 * Time: 17/9/21 10:42  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Decrementer implements Runnable {

    private AtomicIntegerArray vector;

    public Decrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndDecrement(i);
        }
    }

    public static void main(String[] args) {
        final int THREADS = 100;
        AtomicIntegerArray vector = new AtomicIntegerArray(1000);
        Incrementer incrementer = new Incrementer(vector);
        Decrementer decrementer = new Decrementer(vector);

        Thread[] threadIncrementer = new Thread[THREADS];
        Thread[] threadDecrementer = new Thread[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threadIncrementer[i] = new Thread(incrementer);
            threadDecrementer[i] = new Thread(decrementer);

            threadIncrementer[i].start();
            threadDecrementer[i].start();
        }
        try {
            for (int i = 0; i < THREADS; i++) {
                threadIncrementer[i].join();
                threadDecrementer[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0, len = vector.length(); i < len; i++) {
            if (vector.get(i) != 0) {
                System.out.println("Vector[" + i + "] : " + vector.get(i));
            }
        }
        System.out.println("Main: End of the example");
    }

}
