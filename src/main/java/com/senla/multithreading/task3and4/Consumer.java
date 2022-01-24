package com.senla.multithreading.task3and4;

import static com.senla.multithreading.task3and4.Sleeper.sleep;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Runnable {

    private static final int COUNT_NUMBERS = 20;

    private List<Integer> numbers = new ArrayList<>();
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (numbers.size() != COUNT_NUMBERS) {
            sleep(1000);
            Integer number = buffer.getNumber();
            if (number != null) {
                numbers.add(number);
            }
        }
        printNumbers();
    }

    private void printNumbers() {
        for (Integer number : numbers) {
            System.out.print(number + " ");
        }
    }
}
