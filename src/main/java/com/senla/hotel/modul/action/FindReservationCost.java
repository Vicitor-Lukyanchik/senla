package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.formatter.HotelFormatter;
import com.senla.hotel.modul.formatter.HotelFormatterImpl;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindReservationCost implements Action {

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();
    
    private LodgerService lodgerService;

    public FindReservationCost(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput lodger id : ");
        Integer lodgerId = ConsoleReader.readNumber();
        System.out.println(hotelFormatter.formatLodgerReversationsCost(lodgerService.findReservationCostByLodgerId(lodgerId)));
    }
}
