package com.senla.hotel.repository;

import java.util.List;

import com.senla.hotel.domain.Reservation;

public interface ReservationRepository {
    List<Reservation> getReservations();
    
    void addReservation(Reservation reservation);
}
