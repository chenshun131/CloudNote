package com.chenshun.test.concurrency.threadmanager.sync;

/**
 * User: chenshun131 <p />
 * Time: 17/9/1 21:48  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SynchronizedTest implements Runnable {

    private Integer account = 100;

    public void method1(Object obj) {
        // 访问一个对象的synchronized代码块时，别的线程可以访问该对象的非synchronized代码块而不受阻塞
        synchronized (this) { // 锁定当前对象
        }
    }

    public void method2(Object obj) {
        synchronized (obj) { // obj 锁定的对象
        }
    }

    // 当没有明确的对象作为锁，只是想让一段代码同步时，可以创建一个特殊的对象来充当锁
    // 零长度的byte数组对象创建起来将比任何对象都经济――查看编译后的字节码：生成零长度的byte[]对象只需3条操作码，而Object lock = new Object()则需要7行操作码
    private byte[] lock = new byte[0];

    public void method3() {
        synchronized (lock) {
        }
    }

    public synchronized void method4() { // 锁定整个函数
    }

    public synchronized static void method5() { // 修饰静态方法
    }

    @Override
    public synchronized void run() {
        method5();
        account += 1;
        synchronized (account) {
            account -= 1;
        }
    }

    public void method6() {
        synchronized (SynchronizedTest.class) { // 锁定类，给类加锁，类的所有对象用的同一把锁
        }
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }


    public static void main(String[] args) {
        SynchronizedTest run = new SynchronizedTest();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(run);
            thread.start();
        }

        System.out.println("account = " + run.getAccount());
    }

}
