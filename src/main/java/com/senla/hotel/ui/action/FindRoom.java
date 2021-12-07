package com.senla.hotel.ui.action;

import java.util.Arrays;

import com.senla.hotel.domain.Room;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindRoom implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private RoomService roomService;

    public FindRoom() {
        roomService = RoomServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput room id : ");
        Integer id = ConsoleReader.readNumber();
        Room room = roomService.find(id);
        System.out.println(hotelForrmatter.formatRooms(Arrays.asList(room)));
    }
}
