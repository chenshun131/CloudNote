package com.chenshun.test.concurrency.threadsynchronization.readwritelock;

/**
 * User: mew <p />
 * Time: 17/8/31 17:02  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Writer implements Runnable {

    private PriceInfo priceInfo;

    public Writer(PriceInfo priceInfo) {
        this.priceInfo = priceInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.printf("Write: Attempt to modify the prices.\n");
            priceInfo.setPrices(Math.random() * 10, Math.random() * 8);
            System.out.printf("Writer: Prices have been modified.\n");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
