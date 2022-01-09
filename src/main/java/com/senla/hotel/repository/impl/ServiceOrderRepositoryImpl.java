package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.repository.ServiceOrderRepository;

@Singleton
public class ServiceOrderRepositoryImpl implements ServiceOrderRepository {

    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public List<ServiceOrder> getServiceOrders() {
        return new ArrayList<>(serviceOrders);
    }

    public void addServiceOrder(ServiceOrder serviceOrder) {
        serviceOrders.add(serviceOrder);
    }
}
