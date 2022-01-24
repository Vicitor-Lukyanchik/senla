package com.senla.multithreading.task3and4;

public class Sleeper {
    public static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
        }
    }
}
