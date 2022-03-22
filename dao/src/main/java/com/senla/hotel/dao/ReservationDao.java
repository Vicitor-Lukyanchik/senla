package com.senla.hotel.dao;

import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Service;
import org.hibernate.Session;

import java.util.List;

public interface ReservationDao extends GenericDao<Reservation, Long> {
    void create(Reservation entity, Session session);

    void update(Reservation entity, Session session);

    List<Reservation> findAll(Session session, Class<Reservation> type);
}
