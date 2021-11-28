package com.senla.hotel.reader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleReader {

    private static final DateTimeFormatter DATE_TIME_FORRMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");

    private static Scanner scanner = new Scanner(System.in);
    
    public static String readLine() {
        return scanner.nextLine();
    }

    public static int readNumber() {
        return scanner.nextInt();
    }
    
    public static BigDecimal readBigDecimal() {
        return scanner.nextBigDecimal();
    }
    
    public static LocalDate readDate() {
        String date = scanner.nextLine();
        return LocalDate.parse(date, DATE_TIME_FORRMATTER);
    }
}
