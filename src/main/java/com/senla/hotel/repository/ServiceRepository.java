package com.senla.hotel.repository;

import java.util.List;

import com.senla.hotel.domain.Service;

public interface ServiceRepository {
    List<Service> getServices();
    
    void addService(Service service);
}
