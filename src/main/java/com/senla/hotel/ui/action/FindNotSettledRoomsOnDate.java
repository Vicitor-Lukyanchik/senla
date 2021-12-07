package com.senla.hotel.ui.action;

import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindNotSettledRoomsOnDate implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService roomService;

    public FindNotSettledRoomsOnDate() {
        roomService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        ConsoleReader.readLine();
        System.out.print("\nInput date : ");
        List<Room> rooms = roomService.findAllNotSettledRoomOnDate(ConsoleReader.readDate());
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
