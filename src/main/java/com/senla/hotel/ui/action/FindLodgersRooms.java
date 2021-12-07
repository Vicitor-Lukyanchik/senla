package com.senla.hotel.ui.action;

import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindLodgersRooms implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService lodgerService;

    public FindLodgersRooms() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        Map<Lodger, Room> lodgersRooms = lodgerService.findAllNowLodgersRooms();
        System.out.println(hotelForrmatter.formatLodgersRooms(lodgersRooms));
    }
}
