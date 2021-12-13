package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.ServiceOrder;

public interface ServiceOrdersParser {
    List<ServiceOrder> parseServiceOrders(List<String> serviceOrder);

    List<String> parseLines(List<ServiceOrder> serviceOrder);
}
