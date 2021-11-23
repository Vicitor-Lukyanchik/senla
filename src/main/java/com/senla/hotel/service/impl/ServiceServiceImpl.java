package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Service;
import com.senla.hotel.service.ServiceService;

public class ServiceServiceImpl implements ServiceService {
    
    private Hotel hotel;
    private Integer id = 1;

    public ServiceServiceImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void create(Service service) {
        validateService(service);
        service.setId(id);
        id++;
        hotel.addService(service);
    }
    
    private void validateService(Service service) {
        if(service == null) {
            throw new IllegalArgumentException("Service can not be null");
        }
    }
    
    @Override
    public void updateCost(Integer id, BigDecimal cost) {
        Service service = find(id);
        service.setCost(cost);
    }

    @Override
    public Service find(Integer id) {
        for(Service service : hotel.getServices()) {
            if(service.getId().equals(id)) {
                return service;
            }
        }
        throw new IllegalArgumentException("There is not service with this id");
    }
    
    @Override
    public List<Service> findAll() {
        return hotel.getServices();
    }
    
    @Override
    public Hotel getHotel() {
        return hotel;
    }
}
