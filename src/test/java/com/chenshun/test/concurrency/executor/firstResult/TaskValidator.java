package com.chenshun.test.concurrency.executor.firstResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: mew <p />
 * Time: 17/9/7 16:03  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TaskValidator implements Callable<String> {

    private UserValidator validator;

    private String user;

    private String password;

    public TaskValidator(UserValidator validator, String user, String password) {
        this.validator = validator;
        this.user = user;
        this.password = password;
    }

    @Override
    public String call() throws Exception {
        if (!validator.validate(user, password)) {
            System.out.printf("%s: The user has not been found\n", validator.getName());
            throw new Exception("Error validating user");
        }
        System.out.printf("%s: The user has not been found\n", validator.getName());
        return validator.getName();
    }

    public static void main(String[] args) {
        String username = "test";
        String password = "test";
        TaskValidator ldapTask = new TaskValidator(new UserValidator("LDAP"), username, password);
        TaskValidator dbTask = new TaskValidator(new UserValidator("DataBase"), username, password);

        List<TaskValidator> taskList = new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            // invokeAny : 指定给定任务返回其中一个执行成功
            String result = executorService.invokeAny(taskList);
            System.out.printf("Main: Result: %s\n", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.printf("Main: End of the Execution\n");
    }

}
