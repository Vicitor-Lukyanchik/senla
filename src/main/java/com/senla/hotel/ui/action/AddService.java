package com.senla.hotel.ui.action;

import java.math.BigDecimal;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;

public class AddService implements Action {

    private ServiceService serviceService;

    public AddService() {
        serviceService = ServiceServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput service name : ");
        ConsoleReader.readLine();
        String name = ConsoleReader.readLine();
        System.out.print("Input service cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();

        serviceService.create(name, cost);
    }
}
