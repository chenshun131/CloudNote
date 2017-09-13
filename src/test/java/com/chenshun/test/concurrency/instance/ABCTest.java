package com.chenshun.test.concurrency.instance;

/**
 * User: chenshun131 <p />
 * Time: 17/9/2 08:18  <p />
 * Version: V1.0  <p />
 * Description: 建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC <p />
 */
public class ABCTest implements Runnable {

    private String name;

    private Object prev;

    private Object self;

    private ABCTest(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        new Thread(new ABCTest("A", c, a), "Thread A").start();
        Thread.sleep(10);

        new Thread(new ABCTest("B", a, b), "Thread A").start();
        Thread.sleep(10);

        new Thread(new ABCTest("C", b, c), "Thread A").start();
        Thread.sleep(10);
    }

}
