package com.senla.hotel.reader;

import java.util.Scanner;

public class ConsoleReader {

    private static Scanner scanner = new Scanner(System.in);
    
    public static String readLine() {
        return scanner.nextLine();
    }

    public static int readNumber() {
        return scanner.nextInt();
    }
    
    public static double readRealNumber() {
        return scanner.nextDouble();
    }
}
