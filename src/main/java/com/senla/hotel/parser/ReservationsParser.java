package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.Reservation;

public interface ReservationsParser {
    List<Reservation> parseReservations(List<String> reservations);

    List<String> parseLines(List<Reservation> reservations);
}
