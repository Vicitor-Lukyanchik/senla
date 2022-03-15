package com.senla.hotel.dao;

import com.senla.hotel.domain.Reservation;
import org.hibernate.Session;

import java.util.List;

public interface ReservationDao {
    void create(Reservation entity, Session session);

    void createWithId(Reservation entity, Session session);

    void update(Reservation entity, Session session);

    List<Reservation> findAll(Session session, Class<Reservation> type);
}
