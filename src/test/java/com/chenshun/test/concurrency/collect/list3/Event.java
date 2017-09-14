package com.chenshun.test.concurrency.collect.list3;

/**
 * User: mew <p />
 * Time: 17/9/14 14:38  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Event implements Comparable<Event> {

    private int thread;

    private int priority;

    public Event(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }

    public int getThread() {
        return thread;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Event event) {
        if (this.priority > event.getPriority()) {
            return -1;
        } else if (this.priority < event.getPriority()) {
            return 1;
        }
        return 0;
    }

}
