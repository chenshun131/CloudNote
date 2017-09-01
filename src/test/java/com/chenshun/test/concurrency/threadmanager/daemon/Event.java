package com.chenshun.test.concurrency.threadmanager.daemon;

import java.util.Date;

/**
 * User: mew <p />
 * Time: 17/8/29 16:38  <p />
 * Version: V1.0  <p />
 * Description: 守护线程的创建与运行 <p />
 */
public class Event {

    private Date date;

    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
