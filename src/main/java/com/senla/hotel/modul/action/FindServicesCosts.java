package com.senla.hotel.modul.action;

import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Service;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;

public class FindServicesCosts implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private ServiceService roomService;

    public FindServicesCosts(Hotel hotel) {
        roomService = new ServiceServiceImpl(hotel);
    }

    @Override
    public void execute() {
        List<Service> services = roomService.findAll();
        System.out.println(hotelForrmatter.formatServicesCosts(services));
    }
}
