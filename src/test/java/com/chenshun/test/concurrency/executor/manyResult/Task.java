package com.chenshun.test.concurrency.executor.manyResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 17/9/7 19:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Task implements Callable<Result> {

    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public Result call() throws Exception {
        System.out.printf("%s: String\n", this.name);

        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }

        Result result = new Result();
        result.setName(this.name);
        result.setValue(value);
        System.out.println(this.name + ": Ends");
        return result;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Task task = new Task("Task" + i);
            taskList.add(task);
        }

        List<Future<Result>> resultList = null;
        try {
            resultList = executorService.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        System.out.printf("Main: Printing the results");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Result> future = resultList.get(i);

            try {
                Result result = future.get();
                System.out.println(result.getName() + ":" + result.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
