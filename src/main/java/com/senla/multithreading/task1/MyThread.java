package com.senla.multithreading.task1;

public class MyThread implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void notifyThread() {
        notifyAll();
    }
}
