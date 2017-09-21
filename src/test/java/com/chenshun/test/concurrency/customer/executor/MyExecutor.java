package com.chenshun.test.concurrency.customer.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: mew <p />
 * Time: 17/9/21 12:46  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyExecutor extends ThreadPoolExecutor {

    private ConcurrentHashMap<String, Date> startTimes;

    public MyExecutor(int corePoolSize, int maximunPoolSize, long keepAliveTime, TimeUnit unit,
                      BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximunPoolSize, keepAliveTime, unit, workQueue);
        startTimes = new ConcurrentHashMap<>();
    }

    @Override
    public void shutdown() {
        System.out.printf("MyExecutor: Going to shutdown.\n");
        System.out.printf("MyExecutor: Executed tasks:%d\n", getCompletedTaskCount());
        System.out.printf("MyExecutor: Running tasks:%d\n", getActiveCount());
        System.out.printf("MyExecutor: Pending tasks:5d\n", getQueue().size());
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        System.out.printf("MyExecutor: Going to immediately shutdown.\n");
        System.out.printf("MyExecutor: Executed tasks:%d\n", getCompletedTaskCount());
        System.out.printf("MyExecutor: Running tasks:%d\n", getActiveCount());
        System.out.printf("MyExecutor: Pending tasks:5d\n", getQueue().size());
        return super.shutdownNow();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.printf("MyExecutor: A Task is beginning: %s : %s\n", t.getName(), r.hashCode());
        startTimes.put(String.valueOf(r.hashCode()), new Date());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Future<?> result = (Future<?>) r;
        try {
            System.out.printf("*******************************\n");
            System.out.printf("MyExecutor: A Task is finishing.\n");
            System.out.printf("MyExecutor: Result: %s\n", result.get());
            Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
            Date finishDate = new Date();
            long diff = finishDate.getTime() - startDate.getTime();
            System.out.printf("MyExecutor: Duration: %d\n", diff);
            System.out.printf("*******************************\n");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor(2, 4, 1000, TimeUnit.MILLISECONDS
                , new LinkedBlockingDeque<>());
        List<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SleepTwoSecondsTask task = new SleepTwoSecondsTask();
            Future<String> result = myExecutor.submit(task);
            results.add(result);
        }
        try {
            for (int i = 0; i < 5; i++) {
                String result = results.get(i).get();
                System.out.printf("Main: Result for Task %d : %s\n", i, result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        myExecutor.shutdown();
        try {
            for (int i = 5; i < 10; i++) {
                String result = results.get(i).get();
                System.out.printf("Main: Result for Task %d : %s\n", i, result);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            myExecutor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: End of the program.\n");
    }

}
