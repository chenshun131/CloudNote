package com.chenshun.test.concurrency.threadmanager.exception;

/**
 * User: mew <p />
 * Time: 17/8/29 19:00  <p />
 * Version: V1.0  <p />
 * Description: 线程中不可控异常的处理 <p />
 */
public class Task implements Runnable {

    @Override
    public void run() {
        int numero = Integer.parseInt("TTT");
    }

    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }

}
