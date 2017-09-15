package com.chenshun.test.concurrency.collect.delay;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * User: mew <p />
 * Time: 17/9/14 15:39  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    private int id;

    private DelayQueue<Event> queue;

    public Task(int id, DelayQueue<Event> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        Date now = new Date();
        Date delay = new Date();
        delay.setTime(now.getTime() + (id * 1000));
        System.out.printf("Thread %s: %s\n", id, delay);
        for (int i = 0; i < 100; i++) {
            Event event = new Event(delay);
            queue.add(event);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Event> queue = new DelayQueue<>();
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            Task task = new Task(i + 1, queue);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        do {
            int counter = 0;
            Event event;
            do {
                event = queue.poll();
                if (event != null) {
                    counter++;
                }
            } while (event != null);
            System.out.printf("At %s you have read %d events\n", new Date(), counter);
        } while (queue.size() > 0);
    }

}
