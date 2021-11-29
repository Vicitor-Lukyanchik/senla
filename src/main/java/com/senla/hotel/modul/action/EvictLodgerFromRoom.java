package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class EvictLodgerFromRoom implements Action {

    private LodgerService lodgerService;

    public EvictLodgerFromRoom(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput lodger id : ");
        Integer lodgerId = ConsoleReader.readNumber();
        System.out.print("Input room id : ");
        Integer roomId = ConsoleReader.readNumber();

        lodgerService.updateReservationIsReserved(lodgerId, roomId);
    }
}
