package com.chenshun.test.concurrency.threadsynchronization.sync;

/**
 * User: mew <p />
 * Time: 17/8/31 10:24  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Company implements Runnable {

    private Account account;

    public Company(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.addAmount(1000);
        }
    }

}
