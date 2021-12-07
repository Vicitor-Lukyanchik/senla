package com.senla.hotel.ui.action;

import java.math.BigDecimal;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class AddRoom implements Action {

    private static final boolean DEFAULT_REPAIRED = false;

    private RoomService roomService;

    public AddRoom() {
        roomService = RoomServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput room number : ");
        int number = ConsoleReader.readNumber();
        System.out.print("Input room capacity : ");
        int capacity = ConsoleReader.readNumber();
        System.out.print("Input room stars count : ");
        int stars = ConsoleReader.readNumber();
        System.out.print("Input room cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();

        roomService.create(number, cost, capacity, stars, DEFAULT_REPAIRED);
    }
}
