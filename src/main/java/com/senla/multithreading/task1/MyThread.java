package com.senla.multithreading.task1;

import java.util.Scanner;

public class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println("RUNNABLE");

        synchronized (this) {
            try {
                System.out.println("WAITING");
                wait();
            } catch (InterruptedException e) {
            }
        }

        try {
            System.out.println("TIMED_WAITING");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("BLOCKED");
        scanner.nextLine();
        scanner.close();

        System.out.println("TERMINATED");
    }
    
    public synchronized void notifyThread() {
        notifyAll();
    }
}
