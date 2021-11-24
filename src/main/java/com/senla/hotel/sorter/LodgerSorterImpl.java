package com.senla.hotel.sorter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.ServiceOrder;

public class LodgerSorterImpl {
    
    public List<Lodger> sortRoomsByCapacity(List<Lodger> lodgers){
        return lodgers.stream().sorted(Comparator.comparing(Lodger::getFirstLastName)).collect(Collectors.toList());
    }
    
    public List<ServiceOrder> sortServieOrderByDate(List<ServiceOrder> serviceOrders) {
        return serviceOrders.stream().sorted(Comparator.comparing(ServiceOrder::getDate)).collect(Collectors.toList());
    }
}
