package com.senla.hotel.ui.action;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class EvictLodgerFromRoom implements Action {

    private LodgerService lodgerService;

    public EvictLodgerFromRoom() {
        lodgerService = LodgerServiceImpl.getInstance();
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
