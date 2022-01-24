package com.senla.multithreading.task2;

public class Application {

    public static void main(String[] args) {
        Talker talker = new Talker();
        
        Human human1 = new Human("Boris", talker);
        Human human2 = new Human("Pepsi", talker);
        
        Thread thread1 = new Thread(human1);
        Thread thread2 = new Thread(human2);
        
        thread1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        thread2.start();
    }
}
