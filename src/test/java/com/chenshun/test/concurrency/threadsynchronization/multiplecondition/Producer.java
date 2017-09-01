package com.chenshun.test.concurrency.threadsynchronization.multiplecondition;

/**
 * User: mew <p />
 * Time: 17/9/1 09:41  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Producer implements Runnable {

    private FileMock mock;

    private Buffer buffer;

    public Producer(FileMock mock, Buffer buffer) {
        this.mock = mock;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.setPendingLines(true);
        while (mock.hasMoreLines()) {
            String line = mock.getLine();
            buffer.insert(line);
        }
        buffer.setPendingLines(false);
    }

}
