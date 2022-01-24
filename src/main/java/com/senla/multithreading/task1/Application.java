package com.senla.multithreading.task1;

public class Application {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        System.out.println("NEW");
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        myThread.notifyThread();
    }
}
