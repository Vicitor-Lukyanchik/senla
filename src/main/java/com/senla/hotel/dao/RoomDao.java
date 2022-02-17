package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomDao {
    void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired);
    void createWithId(Long id, int number, BigDecimal cost, int capacity, int stars, boolean isRepaired);
    void update(Long id, int number, BigDecimal cost, int capacity, int stars, boolean isRepaired);
    List<Room> findAll();
}
