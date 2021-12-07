package com.senla.hotel.ui.action;

import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindServices implements Action {

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();
    
    private ServiceService lodgerService;

    public FindServices() {
        lodgerService = ServiceServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println(hotelFormatter.formatServices(lodgerService.findAll()));
    }
}
