package com.senla.hotel.dao;

import com.senla.hotel.domain.ServiceOrder;

import java.util.List;

public interface ServiceOrderDao {
    void create(ServiceOrder serviceOrder);
    void createWithId(ServiceOrder serviceOrder);
    void update(ServiceOrder serviceOrder);
    List<ServiceOrder> findAll();
}
