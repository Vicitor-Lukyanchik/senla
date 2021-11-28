package com.senla.hotel.modul.action;

import java.math.BigDecimal;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class AddRoom implements Action {

    private static final boolean DEFAULT_SETTLED = false;
    private static final boolean DEFAULT_REPAIRED = false;
    
    private Hotel hotel;
    private RoomService roomService = new RoomServiceImpl(hotel);
 
    public AddRoom(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        System.out.println("Input room number : ");
        int number = ConsoleReader.readNumber();
        System.out.println("Input room capacity : ");
        int capacity = ConsoleReader.readNumber();
        System.out.println("Input room stars count : ");
        int stars = ConsoleReader.readNumber();
        System.out.println("Input room cost : ");
        BigDecimal cost = new BigDecimal(ConsoleReader.readNumber());
        roomService.create(createRoom(number, cost, capacity, stars));
    }
    
    private Room createRoom(int number, BigDecimal cost, int capacity, int stars) {
        return new Room(number, cost, capacity, stars, DEFAULT_SETTLED, DEFAULT_REPAIRED); 
    }
}
