package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Service;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceDao {
    void create(String name, BigDecimal cost);
    void createWithId(Long id, String name, BigDecimal cost);
    void update(Long id, String name, BigDecimal cost);
    List<Service> findAll();
}
