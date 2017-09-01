package com.chenshun.test.concurrency.threadsynchronization.multiplecondition;

/**
 * User: mew <p />
 * Time: 17/9/1 09:01  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class FileMock {

    private String content[];

    private int index;

    public FileMock(int size, int length) {
        content = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                int indice = (int) (Math.random() * 255);
                stringBuilder.append(indice);
            }
            content[i] = stringBuilder.toString();
        }
        index = 0;
    }

    public boolean hasMoreLines() {
        return index < content.length;
    }

    public String getLine() {
        if (hasMoreLines()) {
            System.out.println("Mock: " + (content.length - index));
            return content[index++];
        }
        return null;
    }

    public static void main(String[] args) {
        FileMock fileMock = new FileMock(100, 10);
        Buffer buffer = new Buffer(20);

        Producer producer = new Producer(fileMock, buffer);
        Thread threadProducer = new Thread(producer, "Producer");

        Consumer[] consumers = new Consumer[3];
        Thread[] threadConsumers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            consumers[i] = new Consumer(buffer);
            threadConsumers[i] = new Thread(consumers[i], "Consumer " + i);
        }

        threadProducer.start();
        for (int i = 0; i < 3; i++) {
            threadConsumers[i].start();
        }
    }

}
