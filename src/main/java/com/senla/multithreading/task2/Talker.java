package com.senla.multithreading.task2;

public class Talker {

    public synchronized void talk(String name) {
        System.out.println(name);
    }
}
