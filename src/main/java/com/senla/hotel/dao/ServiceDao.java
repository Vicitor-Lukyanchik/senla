package com.senla.hotel.dao;

import com.senla.hotel.domain.Service;

import java.sql.Connection;
import java.util.List;

public interface ServiceDao {
    void create(Service service, Connection connection);
    void createWithId(Service service, Connection connection);
    void update(Service service, Connection connection);
    List<Service> findAll(Connection connection);
}
