package com.chenshun.test.concurrency.threadmanager;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

/**
 * User: mew <p />
 * Time: 17/8/29 17:10  <p />
 * Version: V1.0  <p />
 * Description: 守护线程的创建与运行 <p />
 */
public class ClearnerTask extends Thread {

    private Deque<Event> deque;

    public ClearnerTask(Deque<Event> deque) {
        this.deque = deque;

        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            clear();
        }
    }

    private void clear() {
        if (deque.size() == 0) {
            return;
        }
        long difference = 0;
        boolean delete = false;
        do {
            Event e = deque.getLast();
            difference = new Date().getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n", e.getEvent());
                deque.removeLast();
                delete = true;
            }
        } while (!delete);
        if (delete) {
            System.out.printf("Cleaner: Size of the queue: %d\n", deque.size());
        }
    }

    public static void main(String[] args) {
        Deque<Event> deque = new ArrayDeque<>(); // ArrayDeque 不是线程安全的因此执行可能无结果或出现异常，进行断点调试才能得到目标结果

        WriterTask writerTask = new WriterTask(deque);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(writerTask);
            thread.start();
        }

        ClearnerTask clearnerTask = new ClearnerTask(deque);
        clearnerTask.start();
    }

}
