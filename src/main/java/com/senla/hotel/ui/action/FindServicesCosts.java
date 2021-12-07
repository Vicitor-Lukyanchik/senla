package com.senla.hotel.ui.action;

import java.util.List;

import com.senla.hotel.domain.Service;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindServicesCosts implements Action {

    private final HotelFormatter hotelForrmatter = new HotelFormatterImpl();

    private ServiceService roomService;

    public FindServicesCosts() {
        roomService = ServiceServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        List<Service> services = roomService.findAll();
        System.out.println(hotelForrmatter.formatServicesCosts(services));
    }
}
