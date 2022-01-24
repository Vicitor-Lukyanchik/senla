package com.senla.multithreading.task1;

public class Application {

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        System.out.println("NEW");

        thread.start();
        System.out.println("RUNNABLE");
        try {
            System.out.println("TIMED_WAITING");
            thread.sleep(8000);
        } catch (InterruptedException e) {
        }

        thread.off();

        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
            }
            System.out.println("WAITING");
            thread.notify();
        }
        
        System.out.println("TERMINATED");
    }
}
