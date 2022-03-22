package com.senla.hotel.dao;

import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import org.hibernate.Session;

import java.util.List;

public interface RoomDao extends GenericDao<Room, Long> {
    void create(Room entity, Session session);

    void update(Room entity, Session session);

    List<Room> findAll(Session session, Class<Room> type);
}
