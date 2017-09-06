package com.chenshun.test.concurrency.threadsynchronization.cyclic;

/**
 * User: mew <p />
 * Time: 17/9/6 11:33  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Grouper implements Runnable {

    private Results results;

    public Grouper(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        int finalResult = 0;
        System.out.printf("Grouper: Processing results...\n");
        int data[] = results.getData();
        for (int number : data) {
            finalResult += number;
        }
        System.out.printf("Grouper: Total result: %d.\n", finalResult);
    }

}
