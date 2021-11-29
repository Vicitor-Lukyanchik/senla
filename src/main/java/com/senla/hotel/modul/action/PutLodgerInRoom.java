package com.senla.hotel.modul.action;

import java.time.LocalDate;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class PutLodgerInRoom implements Action {

    private LodgerService roomService;

    public PutLodgerInRoom(Hotel hotel) {
        roomService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        ConsoleReader.readLine();
        System.out.print("\nInput start date : ");
        LocalDate startDate = ConsoleReader.readDate();
        System.out.print("Input end date : ");
        LocalDate endDate = ConsoleReader.readDate();
        System.out.print("Input lodger id : ");
        Integer lodgerId = ConsoleReader.readNumber();
        System.out.print("Input room id : ");
        Integer roomId = ConsoleReader.readNumber();

        roomService.createReservation(createReservation(startDate, endDate, lodgerId, roomId));
    }

    private Reservation createReservation(LocalDate startDate, LocalDate endDate, int lodgerId, int roomId) {
        return new Reservation(startDate, endDate, lodgerId, roomId);
    }
}
