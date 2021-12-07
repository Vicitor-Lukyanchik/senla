package com.senla.hotel.ui.action;

import java.time.LocalDate;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class FindLastRoomLodgers implements Action {

    private HotelFormatter hotelFormatter = new HotelFormatterImpl();

    private LodgerService lodgerService;

    public FindLastRoomLodgers() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput room id : ");
        Integer roomId = ConsoleReader.readNumber();
        System.out.print("\nInput how many last reservations : ");
        Integer limit = ConsoleReader.readNumber();

        Map<LocalDate, Lodger> reservations = lodgerService.findLastReservationsByRoomId(roomId, limit);
        System.out.println(hotelFormatter.formatLastRoomReservations(reservations));
    }
}
