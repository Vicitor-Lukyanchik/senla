package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;

import java.sql.Connection;
import java.util.List;

public interface LodgerDao {
    void create(Lodger lodger, Connection connection);
    void createWithId(Lodger lodger, Connection connection);
    void update(Lodger lodger, Connection connection);
    List<Lodger> findAll(Connection connection);
}
