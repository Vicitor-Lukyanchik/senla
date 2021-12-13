package com.senla.hotel.filerepository.impl;

import java.util.List;

import com.senla.hotel.domain.Reservation;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.filerepository.ReservationFileRepository;
import com.senla.hotel.parser.ReservationsParser;
import com.senla.hotel.parser.impl.ReservationsParserImpl;
import com.senla.hotel.reader.FileReader;
import com.senla.hotel.reader.FileReaderImpl;
import com.senla.hotel.writer.FileWriter;
import com.senla.hotel.writer.FileWriterImpl;

public class ReservationFileRepositoryImpl implements ReservationFileRepository {

    private static final String PATH = "reservations.csv";

    private static ReservationFileRepository instance;

    private final FileReader fileReader = new FileReaderImpl();
    private final ReservationsParser reservationParser = new ReservationsParserImpl();
    private final FileWriter fileWriter = new FileWriterImpl();

    private List<Reservation> reservations = getReservationsFromFile();

    private List<Reservation> getReservationsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return reservationParser.parseReservations(lines);
    }

    public static ReservationFileRepository getInstance() {
        if (instance == null) {
            instance = new ReservationFileRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Reservation findById(Long id) {
        Reservation reservation = reservations.stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
        if (reservation != null) {
            return reservation;
        }
        throw new FileRepositoryException("There is not reservation with id = " + id);
    }

    @Override
    public void export(Reservation reservation) {
        try {
            Reservation result = findById(reservation.getId());
            result.setLodgerId(reservation.getLodgerId());
            result.setRoomId(reservation.getRoomId());
            result.setStartDate(reservation.getStartDate());
            result.setEndDate(reservation.getEndDate());
        } catch (FileRepositoryException ex) {
            reservations.add(reservation);
        }
        List<String> lines = reservationParser.parseLines(reservations);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
}
