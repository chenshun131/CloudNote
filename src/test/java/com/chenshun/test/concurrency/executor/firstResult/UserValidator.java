package com.chenshun.test.concurrency.executor.firstResult;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/7 15:58  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class UserValidator {

    private String name;

    public UserValidator(String name) {
        this.name = name;
    }

    public boolean validate(String name, String password) {
        Random random = new Random();
        try {
            long duration = (long) (Math.random() * 10);
            System.out.printf("Validator %s: Validating a user during %d seconds\n", this.name, duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return random.nextBoolean();
    }

    public String getName() {
        return name;
    }

}
