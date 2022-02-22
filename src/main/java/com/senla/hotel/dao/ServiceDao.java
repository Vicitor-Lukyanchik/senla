package com.senla.hotel.dao;

import com.senla.hotel.domain.Service;

import java.util.List;

public interface ServiceDao {
    void create(Service service);
    void createWithId(Service service);
    void update(Service service);
    List<Service> findAll();
}
