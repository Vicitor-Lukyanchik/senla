package com.senla.hotel.repository;

import java.util.List;

import com.senla.hotel.domain.ServiceOrder;

public interface ServiceOrderRepository {
    List<ServiceOrder> getServiceOrders();

    void addServiceOrder(ServiceOrder serviceOrder);

    void setServiceOrders(List<ServiceOrder> serviceOrders);
}
