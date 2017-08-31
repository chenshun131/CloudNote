package com.chenshun.test.concurrency.threadsynchronization.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: mew <p />
 * Time: 17/8/31 16:51  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class PriceInfo {

    private double price1;

    private double price2;

    private ReadWriteLock readWriteLock;

    public PriceInfo() {
        price1 = 1.0;
        price2 = 2.0;
        readWriteLock = new ReentrantReadWriteLock();
    }

    public double getPrice1() {
        readWriteLock.readLock().lock();
        double value = price1;
        readWriteLock.readLock().unlock();
        return value;
    }

    public double getPrice2() {
        readWriteLock.readLock().lock();
        double value = price2;
        readWriteLock.readLock().unlock();
        return value;
    }

    public void setPrices(double price1, double price2) {
        readWriteLock.writeLock().lock();
        this.price1 = price1;
        this.price2 = price2;
        readWriteLock.writeLock().unlock();
    }

    public static void main(String[] args) {
        PriceInfo priceInfo = new PriceInfo();

        Reader readers[] = new Reader[5];
        Thread threadReader[] = new Thread[5];
        for (int i = 0; i < 5; i++) {
            readers[i] = new Reader(priceInfo);
            threadReader[i] = new Thread(readers[i]);
        }

        Writer writer = new Writer(priceInfo);
        Thread threadWiter = new Thread(writer);

        for (int i = 0; i < 5; i++) {
            threadReader[i].start();
        }
        threadWiter.start();
    }

}
