package com.senla.hotel.repository;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Service;

public class ServiceRepositoryImpl implements ServiceRepository {

    private static ServiceRepository instance;
    private List<Service> service = new ArrayList<>();

    public static ServiceRepository getInstance() {
        if (instance == null) {
            instance = new ServiceRepositoryImpl();
        }
        return instance;
    }

    public List<Service> getServices() {
        return service;
    }

    public void addService(Service service) {
        this.service.add(service);
    }
}
