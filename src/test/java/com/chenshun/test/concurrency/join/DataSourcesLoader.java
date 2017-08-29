package com.chenshun.test.concurrency.join;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/29 15:01  <p />
 * Version: V1.0  <p />
 * Description: 等待线程的终止 <p />
 */
public class DataSourcesLoader implements Runnable {

    @Override
    public void run() {
        System.out.printf("Beginning data source loading: %s\n", new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Data source loading has finished: %s\n", new Date());
    }

    public static void main(String[] args) {
        DataSourcesLoader dataSourcesLoader = new DataSourcesLoader();
        Thread thread1 = new Thread(dataSourcesLoader, "DataSourceThread");

        NetworkConnectionsLoader networkConnectionsLoader = new NetworkConnectionsLoader();
        Thread thread2 = new Thread(networkConnectionsLoader, "NetworkConnectionsThread");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Configuration has been loaded:%s\n", new Date());
    }

}
