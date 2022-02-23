package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface RoomDao {
    void create(Room room, Connection connection);
    void createWithId(Room room, Connection connection);
    void update(Room room, Connection connection);
    List<Room> findAll(Connection connection);
}
