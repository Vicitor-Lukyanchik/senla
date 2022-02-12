package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;

import java.util.List;

public interface LodgerDao {
    void create(String firstName, String lastName, String phone);
    void createWithId(Long id, String firstName, String lastName, String phone);
    List<Lodger> findAll();
}
