package com.senla.hotel.modul.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindNotSettledRooms implements Action {

    private static final LocalDate DATE = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));
    
    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService lodgerService;

    public FindNotSettledRooms(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(DATE);
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
