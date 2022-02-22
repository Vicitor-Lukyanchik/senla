package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomDao {
    void create(Room room);
    void createWithId(Room room);
    void update(Room room);
    List<Room> findAll();
}
