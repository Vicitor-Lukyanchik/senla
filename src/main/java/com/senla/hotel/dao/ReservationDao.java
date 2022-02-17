package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {
    void create(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId);
    void createWithId(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId);
    void update(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId);
    List<Reservation> findAll();
}
