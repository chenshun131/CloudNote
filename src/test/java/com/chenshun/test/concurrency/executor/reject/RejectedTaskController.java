package com.chenshun.test.concurrency.executor.reject;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: mew <p />
 * Time: 17/9/11 11:53  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class RejectedTaskController implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("RejectedTaskController: The task %s has been rejected\n", toString().toString());
        System.out.printf("RejectedTaskController: %s\n", executor.toString());
        System.out.printf("RejectedTaskController: Terminating: %s\n", executor.isTerminating());
        System.out.printf("RejectedTaskController: Terminated: %s\n", executor.isTerminated());
    }

    public static void main(String[] args) {
        RejectedTaskController controller = new RejectedTaskController();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setRejectedExecutionHandler(controller);
        System.out.printf("Main: Starting.\n");
        for (int i = 0; i < 3; i++) {
            Task task = new Task("Task" + i);
            executor.submit(task);
        }
        System.out.printf("Main: Shutting down the Executor.\n");
        executor.shutdown();

        System.out.printf("Main: Sending another Task.\n");
        Task task = new Task("RejectedTask");
        executor.submit(task);

        System.out.printf("Main: End");
        System.out.printf("Main: End.\n");
    }

}
