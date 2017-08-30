package com.chenshun.test.concurrency.groupexception;

/**
 * User: mew <p />
 * Time: 17/8/30 09:53  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup(String name) {
        super(name);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("The thread %s has thrown an Exception\n", t.getId());
        e.printStackTrace(System.out);
        System.out.printf("Terminating the rest of the Threads\n");
        interrupt();
    }

    public static void main(String[] args) {
        MyThreadGroup myThreadGroup = new MyThreadGroup("MyThreadGroup");

        Task task = new Task();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(myThreadGroup, task);
            thread.start();
        }
    }

}
