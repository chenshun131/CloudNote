package com.chenshun.test.concurrency.collect.list2;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/14 13:44  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Client implements Runnable {

    private LinkedBlockingDeque<String> requestList;

    public Client(LinkedBlockingDeque<String> requestList) {
        this.requestList = requestList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                StringBuilder request = new StringBuilder();
                request.append(i);
                request.append(":");
                request.append(j);
                try {
                    requestList.put(request.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Client: %s at %s.\n", request, new Date());
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Clients: End.\n");
    }

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>(3);
        Client client = new Client(list);
        Thread thread = new Thread(client);
        thread.start();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String request = list.take();
                System.out.printf("Main: Request: %s at %s. Size: %d\n", request, new Date(), list.size());
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
        System.out.printf("Main: End of the program.\n");
    }

}
