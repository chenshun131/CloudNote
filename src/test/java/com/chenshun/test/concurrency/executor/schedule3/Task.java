package com.chenshun.test.concurrency.executor.schedule3;

import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 17/9/8 13:58  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Callable<String> {

    @Override
    public String call() throws Exception {
        while (true) {
            System.out.printf("Task: Test\n");
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Task task = new Task();
        System.out.printf("Main: Executing the Task\n");
        Future<String> result = executorService.submit(task);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Canceling the Task\n");
        result.cancel(true);
        System.out.printf("Main: Cancelled: %s\n", result.isCancelled());
        System.out.printf("Main: Done: %s\n", result.isDone());
        executorService.shutdown();
        System.out.printf("Main: The executor has finish\n");
    }

}
