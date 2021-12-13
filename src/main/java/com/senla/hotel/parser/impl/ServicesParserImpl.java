package com.senla.hotel.parser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Service;
import com.senla.hotel.parser.ServicesParser;

public class ServicesParserImpl implements ServicesParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<Service> parseServices(List<String> services) {
        List<Service> result = new ArrayList<>();
        for (String service : services) {
            result.add(parseService(service.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Service parseService(String[] service) {
        Long id = Long.parseLong(service[0]);
        String name = service[1];
        BigDecimal cost = new BigDecimal(service[2]);
        return new Service(id, name, cost);
    }

    @Override
    public List<String> parseLines(List<Service> services) {
        List<String> result = new ArrayList<>();
        for (Service service : services) {
           result.add(parseLine(service));
        }
        return result;
    }

    private String parseLine(Service service) {
        return service.getId() + NEXT_COLUMN + service.getName() + NEXT_COLUMN + service.getCost();
    }
}
