package com.senla.hotel.dao;

import com.senla.hotel.domain.Reservation;

import java.sql.Connection;
import java.util.List;

public interface ReservationDao {
    void create(Reservation reservation, Connection connection);
    void createWithId(Reservation reservation, Connection connection);
    void update(Reservation reservation, Connection connection);
    List<Reservation> findAll(Connection connection);
}
