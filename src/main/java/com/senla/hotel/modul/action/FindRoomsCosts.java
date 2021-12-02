package com.senla.hotel.modul.action;

import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class FindRoomsCosts implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private RoomService roomService;

    public FindRoomsCosts(Hotel hotel) {
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void execute() {
        List<Room> rooms = roomService.findAll();
        System.out.println(hotelForrmatter.formatRoomsCosts(rooms));
    }
}
