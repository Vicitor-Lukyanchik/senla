package com.senla.hotel.modul.action;

import java.time.LocalDate;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class OrderServiceToLodger implements Action {

    private LodgerService roomService;

    public OrderServiceToLodger(Hotel hotel) {
        roomService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        ConsoleReader.readLine();
        System.out.print("\nInput date : ");
        LocalDate endDate = ConsoleReader.readDate();
        System.out.print("Input lodger id : ");
        int lodgerId = ConsoleReader.readNumber();
        System.out.print("Input service id : ");
        int serviceId = ConsoleReader.readNumber();
        roomService.createSeviceOrder(createReservation(endDate, lodgerId, serviceId));
    }

    private ServiceOrder createReservation(LocalDate date, int lodgerId, int serviceId) {
        return new ServiceOrder(date, lodgerId, serviceId);
    }
}
