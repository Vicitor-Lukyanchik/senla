package com.senla.hotel.ui.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindNotSettledRooms implements Action {

    private static final LocalDate DATE = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));
    
    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService lodgerService;

    public FindNotSettledRooms() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(DATE);
        System.out.println(hotelForrmatter.formatRooms(rooms));
    }
}
