package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.repository.ReservationRepository;

@Singleton
public class ReservationRepositoryImpl implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
