package com.senla.hotel.ui.action;

import java.math.BigDecimal;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;

public class ChangeServiceCost implements Action {

    private ServiceService serviceService;

    public ChangeServiceCost() {
        serviceService = ServiceServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.print("\nInput service id : ");
        Integer id = ConsoleReader.readNumber();
        System.out.print("Input service cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();
        
        serviceService.updateCost(id, cost);
    }
}
