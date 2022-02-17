package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class ReservationDaoImpl implements ReservationDao {

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId){
        String sql = "INSERT INTO reservations (id, start_date, end_date, lodger_id, room_id) " +
                "VALUES (nextval('reservations_id_seq'), ?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setLong(3, lodgerId);
            statement.setLong(4, roomId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create reservation", e);
        }
    }

    public void createWithId(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId){
        String sql = "INSERT INTO reservations (id, start_date, end_date, lodger_id, room_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setDate(2, Date.valueOf(startDate));
            statement.setDate(3, Date.valueOf(endDate));
            statement.setLong(4, lodgerId);
            statement.setLong(5, roomId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create reservation with id", e);
        }
    }

    public void update(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId){
        String sql = "UPDATE reservations SET start_date= ? , end_date = ?, lodger_id = ?, room_id = ?" +
                "WHERE id = ?";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setLong(3, lodgerId);
            statement.setLong(4, roomId);
            statement.setLong(5, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update reservation", e);
        }
    }

    public List<Reservation> findAll(){
        String sql = "SELECT r.id, r.start_date, r.end_date, r.lodger_id, r.room_id FROM reservations r";
        List<Reservation> reservations = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(buildReservation(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find reservations", e);
        }
        return reservations;
    }

    private Reservation buildReservation(ResultSet resultSet) {
        Reservation reservation = new Reservation();
        try {
            reservation.setId(resultSet.getLong("id"));
            reservation.setStartDate(resultSet.getDate("start_date").toLocalDate());
            reservation.setEndDate(resultSet.getDate("end_date").toLocalDate());
            reservation.setLodgerId(resultSet.getLong("lodger_id"));
            reservation.setRoomId(resultSet.getLong("room_id"));
        } catch (SQLException ex){
            throw new DAOException("Can not parse reservation from resultSet");
        }
        return reservation;
    }
}
