package com.chenshun.test.concurrency.threadmanager;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/29 14:38  <p />
 * Version: V1.0  <p />
 * Description: 线程的休眠和恢复 <p />
 */
public class FileClock implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s\n", new Date());
            try {
                TimeUnit.SECONDS.sleep(1); // 这个 sleep 方法在阻塞线程时，调用的 interrupt() 会导致抛出 InterruptedException
            } catch (InterruptedException e) {
                System.out.println("The FileClock has been interrupted");
            }
        }
    }

    public static void main(String[] args) {
        FileClock fileClock = new FileClock();
        Thread thread = new Thread(fileClock);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5); // 这个 sleep 方法会阻塞 main 线程，之后运行 interrupt() 前线程都正常运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }

}
