package com.chenshun.test.concurrency.customer.executor;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/21 15:21  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class SleepTwoSecondsTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return new Date().toString();
    }

}
