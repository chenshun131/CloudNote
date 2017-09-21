package com.chenshun.test.concurrency.collect.atomic;

/**
 * User: mew <p />
 * Time: 17/9/20 09:53  <p />
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
        for (int i = 0; i < 10; i++) {
            account.subtractAmount(1000);
        }
    }

}
