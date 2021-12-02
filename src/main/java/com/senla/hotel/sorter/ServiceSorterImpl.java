package com.senla.hotel.sorter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.senla.hotel.domain.Service;

public class ServiceSorterImpl {

    public List<Service> sortRoomsByCost(List<Service> services) {
        return services.stream().sorted(Comparator.comparing(Service::getCost)).collect(Collectors.toList());
    }
}
