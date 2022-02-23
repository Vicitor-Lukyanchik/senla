package com.senla.hotel.dao;

import com.senla.hotel.domain.ServiceOrder;

import java.sql.Connection;
import java.util.List;

public interface ServiceOrderDao {
    void create(ServiceOrder serviceOrder, Connection connection);
    void createWithId(ServiceOrder serviceOrder, Connection connection);
    void update(ServiceOrder serviceOrder, Connection connection);
    List<ServiceOrder> findAll(Connection connection);
}
