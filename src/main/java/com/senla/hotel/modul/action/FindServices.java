package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;

public class FindServices implements Action {

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();
    
    private ServiceService lodgerService;

    public FindServices(Hotel hotel) {
        lodgerService = new ServiceServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.println(hotelFormatter.formatServices(lodgerService.findAll()));
    }
}
