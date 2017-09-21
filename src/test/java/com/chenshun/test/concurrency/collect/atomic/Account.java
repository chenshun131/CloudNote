package com.chenshun.test.concurrency.collect.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: mew <p />
 * Time: 17/9/20 09:48  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Account {

    private AtomicLong balance;

    public Account() {
        balance = new AtomicLong();
    }

    public long getBalance() {
        return balance.get();
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    public void addAmount(long amount) {
        this.balance.getAndAdd(amount);
    }

    public void subtractAmount(long amount) {
        this.balance.getAndAdd(-amount);
    }

    public static void main(String[] args) {
        Account account = new Account();
        account.setBalance(1000);

        Company company = new Company(account);
        Thread companyThread = new Thread(company);

        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);

        System.out.printf("Account : Initial Balance: %d\n", account.getBalance());
        companyThread.start();
        bankThread.start();
        try {
            companyThread.join();
            bankThread.join();
            System.out.printf("Account : Final Balance: %d\n", account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
