package com.chenshun.test.concurrency.threadsynchronization.countdown;

import java.util.concurrent.TimeUnit;

/**
 * User: mew <p />
 * Time: 17/9/5 17:28  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class Participant implements Runnable {

    private Videoconference videoconference;

    private String name;

    public Participant(Videoconference videoconference, String name) {
        this.videoconference = videoconference;
        this.name = name;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        videoconference.arrive(name);
    }

    public static void main(String[] args) {
        Videoconference videoconference = new Videoconference(10);
        Thread thread = new Thread(videoconference);
        thread.start();

        for (int i = 0; i < 10; i++) {
            Participant participant = new Participant(videoconference, "Participant " + i);
            new Thread(participant).start();
        }
    }

}
