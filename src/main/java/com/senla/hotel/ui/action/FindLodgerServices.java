package com.senla.hotel.ui.action;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindLodgerServices implements Action {

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();
    
    private LodgerService lodgerService;

    public FindLodgerServices() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput lodger id : ");
        Integer lodgerId = ConsoleReader.readNumber();
        System.out.println(hotelFormatter.formatLodgerServices(lodgerService.findServiceOrderByLodgerId(lodgerId)));
    }

}
