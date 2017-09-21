package com.chenshun.test.concurrency.customer.threadfactory;

import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/9/21 16:21  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class MyThread extends Thread {

    private Date creationDate;

    private Date startDate;

    private Date finishDate;

    public MyThread(Runnable target, String name) {
        super(target, name);
        setCreationDate();
    }

    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    public void setCreationDate() {
        creationDate = new Date();
    }

    public void setStartDate() {
        startDate = new Date();
    }

    public void setFinishDate() {
        finishDate = new Date();
    }

    public long getExecutionTime() {
        return finishDate.getTime() - startDate.getTime();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getName()).append(": ")
                .append(" Creation date: ").append(creationDate)
                .append(" : Running time: ").append(getExecutionTime())
                .append(" Milliseconds.");
        return buffer.toString();
    }

}
