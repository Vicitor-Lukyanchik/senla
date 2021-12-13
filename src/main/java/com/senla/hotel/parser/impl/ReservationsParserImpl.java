package com.senla.hotel.parser.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Reservation;
import com.senla.hotel.parser.ReservationsParser;

public class ReservationsParserImpl implements ReservationsParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<Reservation> parseReservations(List<String> reservations) {
        List<Reservation> result = new ArrayList<>();
        for (String reservation : reservations) {
            result.add(parseLodger(reservation.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Reservation parseLodger(String[] reservation) {
        Long id = Long.parseLong(reservation[0]);
        LocalDate startDate = LocalDate.parse(reservation[1], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        LocalDate endDate = LocalDate.parse(reservation[2], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long lodgerId = Long.parseLong(reservation[3]);
        Long roomId = Long.parseLong(reservation[4]);
        return new Reservation(id, startDate, endDate, lodgerId, roomId);
    }

    @Override
    public List<String> parseLines(List<Reservation> reservations) {
        List<String> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            result.add(parseLine(reservation));
        }
        return result;
    }

    private String parseLine(Reservation reservation) {
        return reservation.getId() + NEXT_COLUMN + reservation.getStartDate() + NEXT_COLUMN + reservation.getEndDate()
                + reservation.getLodgerId() + NEXT_COLUMN + reservation.getRoomId() + NEXT_COLUMN;
    }
}
