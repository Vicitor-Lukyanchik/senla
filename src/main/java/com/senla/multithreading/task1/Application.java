package com.senla.multithreading.task1;

public class Application {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        System.out.println(thread.getState());
        thread.start();

        //Run
        System.out.println(thread.getState());

        //Wait
        sleep(500);
        System.out.println(thread.getState());
        myThread.notifyThread();

        //Timed-waiting
        sleep(500);
        System.out.println(thread.getState());
        myThread.notifyThread();

        //Blocked
        System.out.println(thread.getState());
        myThread.notifyThread();

        //Terminated
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
        System.out.println(thread.getState());
    }

    private static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
        }
    }
}
