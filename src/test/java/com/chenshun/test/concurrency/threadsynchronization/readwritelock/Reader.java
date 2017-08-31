package com.chenshun.test.concurrency.threadsynchronization.readwritelock;

/**
 * User: mew <p />
 * Time: 17/8/31 16:58  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Reader implements Runnable {

    private PriceInfo priceInfo;

    public Reader(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%s: Price 1: %f\n", Thread.currentThread().getName(), priceInfo.getPrice1());
            System.out.printf("%s: Price 2: %f\n", Thread.currentThread().getName(), priceInfo.getPrice2());
        }
    }

}
