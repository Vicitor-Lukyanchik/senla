package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.repository.ServiceOrderRepository;

public class ServiceOrderRepositoryImpl implements ServiceOrderRepository {

    private static ServiceOrderRepository instance;
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public static ServiceOrderRepository getInstance() {
        if (instance == null) {
            instance = new ServiceOrderRepositoryImpl();
        }
        return instance;
    }

    public List<ServiceOrder> getServiceOrders() {
        return new ArrayList<>(serviceOrders);
    }

    public void addServiceOrder(ServiceOrder serviceOrder) {
        serviceOrders.add(serviceOrder);
    }
}