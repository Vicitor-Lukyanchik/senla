package com.senla.hotel.service;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Service;

public interface ServiceService {

    void create(String name, BigDecimal cost);

    void updateCost(Long id, BigDecimal cost);

    Service find(Long id);

    List<Service> findAll();
}
