package com.senla.hotel.modul.action;

import java.math.BigDecimal;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Service;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;

public class AddService implements Action {

    private ServiceService serviceService;

    public AddService(Hotel hotel) {
        serviceService = new ServiceServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput service name : ");
        ConsoleReader.readLine();
        String name = ConsoleReader.readLine();
        System.out.print("Input service cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();

        serviceService.create(createService(name, cost));
    }

    private Service createService(String name, BigDecimal cost) {
        return new Service(name, cost);
    }
}
