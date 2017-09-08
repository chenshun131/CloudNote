package com.chenshun.test.concurrency.executor.schedule;

import java.util.Date;
import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 17/9/7 20:01  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Callable<String> {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.printf("%s: Starting at : %s\n", name, new Date());
        return "Hello, world";
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s\n", new Date());
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task " + i);
            executor.schedule(task, i + 1, TimeUnit.SECONDS);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Ends at: %s\n", new Date());
    }

}
