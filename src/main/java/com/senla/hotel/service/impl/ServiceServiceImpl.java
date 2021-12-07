package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.repository.ServiceRepository;
import com.senla.hotel.repository.ServiceRepositoryImpl;
import com.senla.hotel.service.ServiceService;

public class ServiceServiceImpl implements ServiceService {

    private static ServiceService instance;
    
    private ServiceRepository serviceRepository;
    private Long id = 1l;

    public ServiceServiceImpl() {
        serviceRepository = ServiceRepositoryImpl.getInstance();
    }

    public static ServiceService getInstance () {
        if(instance == null) {
            instance = new ServiceServiceImpl();
        }
        return instance;
    }
    
    @Override
    public void create(String name, BigDecimal cost) {
        validateService(name);
        serviceRepository.addService(new Service(id, name, cost));
        id++;
    }

    private void validateService(String name) {
        if (name.equals("")) {
            throw new ServiceException("Service neme can not be empty");
        }
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Service service = findById(id);
        service.setCost(cost);
    }

    @Override
    public Service findById(Long id) {
        for (Service service : serviceRepository.getServices()) {
            if (service.getId().equals(id)) {
                return service;
            }
        }
        throw new ServiceException("There is not service with this id");
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.getServices();
    }
}
