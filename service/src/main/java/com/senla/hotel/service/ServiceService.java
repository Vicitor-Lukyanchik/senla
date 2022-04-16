package com.senla.hotel.service;

import com.senla.hotel.entity.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceService {

    void create(Service service);

    void importServices();

    void exportService(Long id);

    void updateCost(Long id, BigDecimal cost);

    Service findById(Long id);

    List<Service> findAll();
}
