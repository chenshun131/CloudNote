package com.chenshun.test.concurrency.executor.executorframework;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: mew <p />
 * Time: 17/9/7 11:06  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Server {

    private ThreadPoolExecutor executor;

    public Server() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void executeTask(Task task) {
        System.out.printf("Server: A new Task has arrived\n");
        executor.execute(task);
        System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());
        System.out.printf("Server: Active Count: %d\n", executor.getActiveCount());
        System.out.printf("Server: Complete Size: %d\n", executor.getCompletedTaskCount());
    }

    public void endServer() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        Server server = new Server();
        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task " + i);
            server.executeTask(task);
        }
        server.endServer();

//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        for (int i = 0; i < 5; i++) {
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
//        }
//        executor.shutdown();
    }

}
