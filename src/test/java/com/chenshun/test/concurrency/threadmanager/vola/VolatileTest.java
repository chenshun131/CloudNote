package com.chenshun.test.concurrency.threadmanager.vola;

/**
 * User: chenshun131 <p />
 * Time: 17/9/1 23:42  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class VolatileTest implements Runnable {

    private volatile int i = 100;

    @Override
    public void run() {
        i += 1;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        i -= 1;
    }

    public int getI() {
        return i;
    }

    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(volatileTest);
            thread.start();
        }

        System.out.println("i = " + volatileTest.getI());
    }

}
