package com.senla.hotel.service;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Service;

public interface ServiceService {

    void create(Service service);

    void updateCost(Integer id, BigDecimal cost);

    Service find(Integer id);

    List<Service> findAll();

    Hotel getHotel();
}
