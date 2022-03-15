package com.senla.hotel.dao;

import com.senla.hotel.domain.Room;
import org.hibernate.Session;

import java.util.List;

public interface RoomDao {
    void create(Room entity, Session session);

    void createWithId(Room entity, Session session);

    void update(Room entity, Session session);

    List<Room> findAll(Session session, Class<Room> type);
}
