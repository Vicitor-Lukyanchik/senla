package com.senla.multithreading.task3;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private static final int MAX_COUNT = 10;
    private static final int MIN_COUNT = 0;

    private List<Integer> numbers = new ArrayList<>(10);

    public synchronized void putNumber(int number) {
        if (numbers.size() < MAX_COUNT) {
            notifyAll();
            numbers.add(number);
            System.out.println("Put number : " + number);
        } else {
            try {
                System.out.println("Max count numbers");
                wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized Integer getNumber() {
        if (numbers.size() > MIN_COUNT) {
            notifyAll();
            int number = numbers.get(0);
            numbers.remove(0);
            System.out.println("Get number : " + number);
            return number;
        } else {
            try {
                System.out.println("Min count numbers");
                wait();
            } catch (InterruptedException e) {
            }
        }
        return null;
    }
}
