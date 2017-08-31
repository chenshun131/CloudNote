package com.chenshun.test.concurrency.threadmanager.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * User: mew <p />
 * Time: 17/8/30 19:01  <p />
 * Version: V1.0  <p />
 * Description: 使用工厂类创建线程 <p />
 */
public class MyThreadFactory implements ThreadFactory {

    private int counter;

    private String name;

    private List<String> stats;

    public MyThreadFactory(String name) {
        this.counter = 0;
        this.name = name;
        this.stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("Create thread %d with name %s on %s\n", thread.getId(), thread.getName(), new Date()));
        return thread;
    }

    public String getStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterable = stats.iterator();
        while (iterable.hasNext()) {
            stringBuilder.append(iterable.next());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        MyThreadFactory myThreadFactory = new MyThreadFactory("MyThreadFactory");
        Task task = new Task();

        Thread thread;
        System.out.printf("Starting the Threads\n");
        for (int i = 0; i < 10; i++) {
            thread = myThreadFactory.newThread(task);
            thread.start();
        }

        System.out.printf("Factory status:\n");
        System.out.printf("%s\n", myThreadFactory.getStatus());
    }

}
