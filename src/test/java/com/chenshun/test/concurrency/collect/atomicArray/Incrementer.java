package com.chenshun.test.concurrency.collect.atomicArray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * User: mew <p />
 * Time: 17/9/21 10:40  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Incrementer implements Runnable {

    private AtomicIntegerArray vector;

    public Incrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndIncrement(i);
        }
    }

}
