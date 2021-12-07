package com.senla.hotel.repository;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.ServiceOrder;

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
        return serviceOrders;
    }

    public void addServiceOrder(ServiceOrder serviceOrder) {
        serviceOrders.add(serviceOrder);
    }
}
