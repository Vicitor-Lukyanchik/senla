package com.senla.multithreading.task3;

import java.util.Random;

public class Producer implements Runnable {

    private static final int MAX_NUMBER = 99;
    private static final int COUNT_NUMBERS = 20;
    
    private Buffer buffer;
    
    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i = 0; i < COUNT_NUMBERS; i++) {
            int number = getRandomNumber();
            buffer.putNumber(number);
            sleep(1000);
        }
    }
    
    private int getRandomNumber() {
        Random randomer = new Random();
        return randomer.nextInt(MAX_NUMBER);
    }
    
    private void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
        }
    }
}
