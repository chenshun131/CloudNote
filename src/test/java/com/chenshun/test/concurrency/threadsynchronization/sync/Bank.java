package com.chenshun.test.concurrency.threadsynchronization.sync;

/**
 * User: mew <p />
 * Time: 17/8/31 10:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Bank implements Runnable {

    private Account account;

    public Bank(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.subtractAmount(1000);
        }
    }

}
