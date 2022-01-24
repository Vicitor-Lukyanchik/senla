package com.senla.multithreading.task2;

public class Human implements Runnable {

    private Talker talker;
    private String name;
    
    public Human(String name, Talker talker) {
        this.name = name;
        this.talker = talker;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            talker.talk(name);
        }
    }
}
