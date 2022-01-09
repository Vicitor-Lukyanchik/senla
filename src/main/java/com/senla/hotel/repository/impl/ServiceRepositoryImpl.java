package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Service;
import com.senla.hotel.repository.ServiceRepository;

@Singleton
public class ServiceRepositoryImpl implements ServiceRepository {

    private List<Service> service = new ArrayList<>();

    public List<Service> getServices() {
        return new ArrayList<>(service);
    }

    public void addService(Service service) {
        this.service.add(service);
    }
}
