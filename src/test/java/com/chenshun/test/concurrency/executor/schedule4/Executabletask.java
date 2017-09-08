package com.chenshun.test.concurrency.executor.schedule4;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/8 15:32  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Executabletask implements Callable<String> {

    private String name;

    public Executabletask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        long duration = (long) (Math.random() * 10);
        System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
        TimeUnit.SECONDS.sleep(duration);
        return "Hellow, world.I'm " + name;
    }

}
