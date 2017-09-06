package com.chenshun.test.concurrency.threadsynchronization.cyclic;

/**
 * User: mew <p />
 * Time: 17/9/6 11:19  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Results {

    private int data[];

    public Results(int size) {
        data = new int[size];
    }

    public void setData(int position, int value) {
        data[position] = value;
    }

    public int[] getData() {
        return data;
    }

}
