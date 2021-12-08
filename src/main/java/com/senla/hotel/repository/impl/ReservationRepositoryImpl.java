package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Reservation;
import com.senla.hotel.repository.ReservationRepository;

public class ReservationRepositoryImpl implements ReservationRepository {

    private static ReservationRepository instance;
    private List<Reservation> reservations = new ArrayList<>();

    public static ReservationRepository getInstance() {
        if (instance == null) {
            instance = new ReservationRepositoryImpl();
        }
        return instance;
    }

    public List<Reservation> getReservations() {
        return  new ArrayList<>(reservations);
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}
