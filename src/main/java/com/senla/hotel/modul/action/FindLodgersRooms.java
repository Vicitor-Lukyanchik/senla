package com.senla.hotel.modul.action;

import java.util.Map;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindLodgersRooms implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private LodgerService lodgerService;

    public FindLodgersRooms(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        Map<Lodger, Room> lodgersRooms = lodgerService.findAllNowLodgersRooms();
        System.out.println(hotelForrmatter.formatLodgersRooms(lodgersRooms));
    }
}
