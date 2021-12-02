package com.senla.hotel.modul.action;

import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindNotSettledRoomsOnDate implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService roomService;

    public FindNotSettledRoomsOnDate(Hotel hotel) {
        roomService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        ConsoleReader.readLine();
        System.out.print("\nInput date : ");
        List<Room> rooms = roomService.findAllNotSettledRoomOnDate(ConsoleReader.readDate());
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
