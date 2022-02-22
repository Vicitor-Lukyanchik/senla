package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;

import java.util.List;

public interface LodgerDao {
    void create(Lodger lodger);
    void createWithId(Lodger lodger);
    void update(Lodger lodger);
    List<Lodger> findAll();
}
