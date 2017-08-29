package com.chenshun.test.concurrency.join;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/29 15:07  <p />
 * Version: V1.0  <p />
 * Description: 等待线程的终止 <p />
 */
public class NetworkConnectionsLoader implements Runnable {

    @Override
    public void run() {
        System.out.printf("Beginning network connection loading: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Network connection loading has finished: %s\n", new Date());
    }

}