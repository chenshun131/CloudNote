package com.chenshun.test.concurrency.collect.list;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * User: mew <p />
 * Time: 17/9/14 08:53  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class AddTask implements Runnable {

    private ConcurrentLinkedDeque<String> list;

    public AddTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10000; i++) {
            list.add(name + ": Element " + i);
        }
    }

}
