package com.chenshun.test.concurrency.threadmanager.exception;

/**
 * User: mew <p />
 * Time: 17/8/29 18:54  <p />
 * Version: V1.0  <p />
 * Description: 线程中不可控异常的处理 <p />
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s\n", t.getId());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace:\n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n", t.getState());
    }

}
