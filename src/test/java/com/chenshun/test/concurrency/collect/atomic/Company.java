package com.chenshun.test.concurrency.collect.atomic;

/**
 * User: mew <p />
 * Time: 17/9/20 09:52  <p />
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
        for (int i = 0; i < 10; i++) {
            account.addAmount(1000);
        }
    }

}
