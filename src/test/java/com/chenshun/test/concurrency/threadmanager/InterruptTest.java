package com.chenshun.test.concurrency.threadmanager;

/**
 * User: mew <p />
 * Time: 17/9/1 17:52  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class InterruptTest {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int i = 0;
            try {
                while (i < 1000) {
                    Thread.sleep(500);
                    System.out.println(i++);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        InterruptTest main = new InterruptTest();
        Thread t = new Thread(main.runnable);
        System.out.println("mainmainmain");
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }

}
