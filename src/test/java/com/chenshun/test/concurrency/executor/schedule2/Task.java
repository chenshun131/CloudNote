package com.chenshun.test.concurrency.executor.schedule2;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/8 08:35  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Runnable {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Start at : %s\n", name, new Date());
        try {
            TimeUnit.MILLISECONDS.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: End at : %s\n", name, new Date());
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s\n", new Date());

        Task task = new Task("Task");
        ScheduledFuture<?> result = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
        for (int i = 0; i < 20; i++) {
            System.out.printf("Main: Delay: %d\n", result.getDelay(TimeUnit.MILLISECONDS));
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Finished at: %s\n", new Date());
    }

}
