package com.chenshun.test.concurrency.threadsynchronization.multiplecondition;

import java.util.Random;

/**
 * User: mew <p />
 * Time: 17/9/1 09:48  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (buffer.hasPendingLines()) {
            String line = buffer.get();
            processLine(line);
        }
    }

    private void processLine(String line) {
        try {
            Random random = new Random();
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
