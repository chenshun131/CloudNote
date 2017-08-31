package com.chenshun.test.concurrency.threadsynchronization.condition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: mew <p />
 * Time: 17/8/31 11:45  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class EventStorage {

    private int maxSize;

    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new ArrayList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.printf("Set: %d\n\n", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Get: %d: %s\n\n", storage.size(), storage.remove(0));
        notifyAll();
    }

    public static void main(String[] args) {
        EventStorage eventStorage = new EventStorage();

        Producer producer = new Producer(eventStorage);
        Thread thread1 = new Thread(producer);

        Consumer consumer = new Consumer(eventStorage);
        Thread thread2 = new Thread(consumer);

        thread1.start();
        thread2.start();
    }

}
