package com.senla.multithreading.task3and4;

public class Applicaiton {

    public static void main(String[] args) {
        Timer timer = new Timer(5000, System.currentTimeMillis());
        Thread timerThread = new Thread(timer);
        timerThread.setDaemon(true);
        timerThread.start();
        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
