package com.chenshun.test.concurrency.executor.completion;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/8 16:22  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ReportGenerator implements Callable<String> {

    private String sender;

    private String title;

    public ReportGenerator(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }

    @Override
    public String call() throws Exception {
        long duration = (long) (Math.random() * 10);

        System.out.printf("%s_%s: ReportGenerator: Generationg a report during %d seconds\n", sender, title, duration);

        TimeUnit.SECONDS.sleep(duration);
        return sender + " : " + title;
    }

}
