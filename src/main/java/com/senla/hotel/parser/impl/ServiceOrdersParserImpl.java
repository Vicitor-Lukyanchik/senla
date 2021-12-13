package com.senla.hotel.parser.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.parser.ServiceOrdersParser;

public class ServiceOrdersParserImpl implements ServiceOrdersParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<ServiceOrder> parseServiceOrders(List<String> serviceOrders) {
        List<ServiceOrder> result = new ArrayList<>();
        for (String serviceOrder : serviceOrders) {
            result.add(parseServiceOrder(serviceOrder.split(NEXT_COLUMN)));
        }
        return result;
    }

    private ServiceOrder parseServiceOrder(String[] serviceOrder) {
        Long id = Long.parseLong(serviceOrder[0]);
        LocalDate date = LocalDate.parse(serviceOrder[1], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long lodgerId = Long.parseLong(serviceOrder[2]);
        Long serviceId = Long.parseLong(serviceOrder[3]);
        return new ServiceOrder(id, date, lodgerId, serviceId);
    }

    @Override
    public List<String> parseLines(List<ServiceOrder> serviceOrders) {
        List<String> result = new ArrayList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(parseLine(serviceOrder));
        }
        return result;
    }

    private String parseLine(ServiceOrder serviceOrder) {
        return serviceOrder.getId() + NEXT_COLUMN + serviceOrder.getDate() + NEXT_COLUMN + serviceOrder.getLodgerId() + NEXT_COLUMN
                + serviceOrder.getServiceId();
    }
}
