package com.chenshun.test.concurrency.executor.completion;

import java.util.concurrent.ExecutorCompletionService;

/**
 * User: mew <p />
 * Time: 17/9/8 16:28  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class ReportRequest implements Runnable {

    private String name;

    private ExecutorCompletionService<String> service;

    public ReportRequest(String name, ExecutorCompletionService<String> service) {
        this.name = name;
        this.service = service;
    }

    @Override
    public void run() {
        ReportGenerator reportGenerator = new ReportGenerator(name, "Report");
        service.submit(reportGenerator);
    }

}
