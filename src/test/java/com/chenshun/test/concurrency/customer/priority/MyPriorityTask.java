package com.chenshun.test.concurrency.customer.priority;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/21 15:40  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyPriorityTask implements Runnable, Comparable<MyPriorityTask> {

    private int priority;

    private String name;

    public MyPriorityTask(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(MyPriorityTask o) {
        if (this.priority < o.getPriority()) {
            return 1;
        } else if (this.priority > o.getPriority()) {
            return -1;
        }
        return 0;
    }

    @Override
    public void run() {
        System.out.printf("MyPriorityTask: %s Priority : %d\n", name, priority);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1,
                TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 4; i++) {
            MyPriorityTask task = new MyPriorityTask(i, "Task " + i);
            executor.execute(task);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 4; i < 8; i++) {
            MyPriorityTask task = new MyPriorityTask(i, "Task " + i);
            executor.execute(task);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: End of the program.\n");
    }

}
