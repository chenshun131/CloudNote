package com.chenshun.component.service;

import org.springframework.core.task.TaskExecutor;

/**
 * User: mew <p />
 * Time: 17/7/11 14:18  <p />
 * Version: V1.0  <p />
 * Description: 任务执行器 <p />
 */
public class TaskExecutorService {

    private TaskExecutor taskExecutor;

    public void addTask(Runnable task) {
        taskExecutor.execute(task);
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}
