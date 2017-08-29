package com.chenshun.test.concurrency;

/**
 * User: mew <p />
 * Time: 17/8/29 10:02  <p />
 * Version: V1.0  <p />
 * Description: 获取线程信息 <p />
 */
public class Calculator implements Runnable {

    private int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        showThreadInfo();

        for (int i = 1; i <= 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }

    private static void showThreadInfo() {
        Thread current = Thread.currentThread();
        System.out.println("ID:" + current.getId() + " Name:" + current.getName() + " Priority:" +
                current.getPriority() + " Status:" + current.getState());
    }

    public static void main(String[] args) {
        showThreadInfo();
        for (int i = 1; i <= 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            if (i % 2 == 0) {
                thread.setPriority(Thread.MAX_PRIORITY);
            } else {
                thread.setPriority(Thread.MIN_PRIORITY);
            }
            thread.start();
        }
    }

}
