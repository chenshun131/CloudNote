package com.chenshun.test.concurrency.threadmanager;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/8/29 14:08  <p />
 * Version: V1.0  <p />
 * Description: 线程中断控 <p />
 */
public class FileSearch implements Runnable {

    private String initPath;

    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(initPath);
        if (file.isDirectory()) {
            try {
                directoryProcess(file);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void directoryProcess(File file) throws InterruptedException {
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
        if (Thread.interrupted()) { // 当调用 interrupt 方法时中断状态被设置，此时返回true，且中断状态将会被清理
            throw new InterruptedException();
        }
    }

    private void fileProcess(File file) throws InterruptedException {
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        if (Thread.interrupted()) { // 当调用 interrupt 方法时中断状态被设置，此时返回true，且中断状态将会被清理
            throw new InterruptedException();
        }
    }

    public static void main(String[] args) {
        FileSearch fileSearch = new FileSearch("/Users/mew/Desktop", "a.txt");
        Thread thread = new Thread(fileSearch);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

}
