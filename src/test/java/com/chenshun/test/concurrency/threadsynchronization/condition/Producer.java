package com.chenshun.test.concurrency.threadsynchronization.condition;

/**
 * User: mew <p />
 * Time: 17/8/31 15:05  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Producer implements Runnable {

    private EventStorage eventStorage;

    public Producer(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            eventStorage.set();
        }
    }

}
