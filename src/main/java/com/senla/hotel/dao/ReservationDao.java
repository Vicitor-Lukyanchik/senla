package com.senla.hotel.dao;

import com.senla.hotel.domain.Reservation;

import java.util.List;

public interface ReservationDao {
    void create(Reservation reservation);
    void createWithId(Reservation reservation);
    void update(Reservation reservation);
    List<Reservation> findAll();
}
