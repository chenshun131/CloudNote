package com.chenshun.test.streamapi.exe;

/**
 * User: chenshun131 <p />
 * Time: 17/9/25 23:17  <p />
 * Version: V1.0  <p />
 * Description: 交易类 <p />
 */
public class Transaction {

    private Trader trader;

    private int year;

    private int value;

    public Transaction() {
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction [trader=" + trader + ", year=" + year + ", value=" + value + "]";
    }

}
