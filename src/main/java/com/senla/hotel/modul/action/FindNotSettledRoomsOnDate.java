package com.senla.hotel.modul.action;

import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class FindNotSettledRoomsOnDate implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private RoomService roomService;

    public FindNotSettledRoomsOnDate(Hotel hotel) {
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void execute() {
        ConsoleReader.readLine();
        System.out.print("\nInput date : ");
        List<Room> rooms = roomService.findAllNotSettledOnDate(ConsoleReader.readDate());
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
