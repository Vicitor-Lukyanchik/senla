package com.senla.multithreading.task1;

public class MyThread implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait();
                Thread.sleep(1000);
                wait(1000);

            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void notifyThread() {
        notify();
    }
}
