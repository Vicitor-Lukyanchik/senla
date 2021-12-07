package com.senla.hotel.ui.action;

import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindRooms implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private RoomService roomService;

    public FindRooms() {
        roomService = RoomServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        List<Room> rooms = roomService.findAll();
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
