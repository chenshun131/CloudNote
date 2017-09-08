package com.chenshun.test.concurrency.executor.schedule4;

import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 17/9/8 15:40  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ResultTask extends FutureTask<String> {

    private String name;

    public ResultTask(Executabletask callable) {
        super(callable);
        this.name = callable.getName();
    }

    @Override
    protected void done() {
        if (isCancelled()) {
            System.out.printf("%s: Has been canceled\n", name);
        } else {
            System.out.printf("%s: Has been finished\n", name);
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        ResultTask[] resultTasks = new ResultTask[5];
        for (int i = 0; i < 5; i++) {
            Executabletask executabletask = new Executabletask("Task " + i);
            resultTasks[i] = new ResultTask(executabletask);
            executor.submit(resultTasks[i]);
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < resultTasks.length; i++) {
            resultTasks[i].cancel(true);
        }
        for (int i = 0; i < resultTasks.length; i++) {
            try {
                if (!resultTasks[i].isCancelled()) {
                    System.out.printf("%s\n", resultTasks[i].get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

}
