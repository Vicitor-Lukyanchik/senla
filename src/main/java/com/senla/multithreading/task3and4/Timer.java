package com.senla.multithreading.task3and4;

import static com.senla.multithreading.task3and4.Sleeper.sleep;

public class Timer implements Runnable {

    private long startTime;
    private long time;

    public Timer(long time, long startTime) {
        this.time = time;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while (true) {
            sleep(time);
            System.out.println(System.currentTimeMillis() - startTime + " milis");
        }
    }
}
