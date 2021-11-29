package com.senla.hotel.modul.action;

import java.math.BigDecimal;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class ChangeRoomCost implements Action {

    private RoomService roomService;

    public ChangeRoomCost(Hotel hotel) {
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput room number : ");
        Integer id = ConsoleReader.readNumber();
        System.out.print("Input room cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();

        roomService.updateCost(id, cost);
    }
}
