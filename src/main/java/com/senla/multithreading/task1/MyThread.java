package com.senla.multithreading.task1;

import java.util.Scanner;

public class MyThread extends Thread {

    private volatile boolean flag = true;

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("BLOCKED");
        sc.nextLine();
        while (flag) {
        }
    }

    public void off() {
        flag = false;
    }
}
