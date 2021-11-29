package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class FindCountNotSettledRooms implements Action {

    private RoomService roomService;

    public FindCountNotSettledRooms(Hotel hotel) {
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.println("\nCount not settled rooms : " + roomService.findAllNotSettled().size());
    }
}
