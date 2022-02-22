package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.senla.hotel.dao.TableColumns.*;

@Singleton
public class ReservationDaoImpl implements ReservationDao {

    private static final String RESERVATION_TABLE = "reservations";
    private static final String RESERVATION_SEQUENCE = "nextval('reservations_id_seq')";
    private static final String INSERT_RESERVATION = "INSERT INTO" + RESERVATION_TABLE +
            "(" + RESERVATION_ID +"," + RESERVATION_START_DATE + "," + RESERVATION_END_DATE +
            "," + RESERVATION_LODGER_ID + "," + RESERVATION_ROOM_ID + ") " +
            "VALUES (" + RESERVATION_SEQUENCE + ", ?, ?, ?, ?)";

    private static final String INSERT_RESERVATION_WITH_ID = "INSERT INTO" + RESERVATION_TABLE +
            "(" + RESERVATION_ID +", " + RESERVATION_START_DATE + ", " + RESERVATION_END_DATE +
            ", " + RESERVATION_LODGER_ID + ", " + RESERVATION_ROOM_ID + ") " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_RESERVATION = "UPDATE " + RESERVATION_TABLE +
            " SET " + RESERVATION_START_DATE + " = ?, " + RESERVATION_END_DATE + " = ?, "
            + RESERVATION_LODGER_ID + " = ?, " + RESERVATION_ROOM_ID + " = ? " +
            "WHERE " + RESERVATION_ID + " = ?";

    private static final String SELECT_RESERVATIONS = "SELECT " + RESERVATION_ID +", " + RESERVATION_START_DATE + ", " + RESERVATION_END_DATE +
            ", " + RESERVATION_LODGER_ID + ", " + RESERVATION_ROOM_ID + " FROM " + RESERVATION_TABLE;

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(Reservation reservation){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_RESERVATION, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setDate(1, Date.valueOf(reservation.getStartDate()));
            statement.setDate(2, Date.valueOf(reservation.getEndDate()));
            statement.setLong(3, reservation.getLodgerId());
            statement.setLong(4, reservation.getRoomId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create reservation", e);
        }
    }

    public void createWithId(Reservation reservation){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_RESERVATION_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setLong(1, reservation.getId());
            statement.setDate(2, Date.valueOf(reservation.getStartDate()));
            statement.setDate(3, Date.valueOf(reservation.getEndDate()));
            statement.setLong(4, reservation.getLodgerId());
            statement.setLong(5, reservation.getRoomId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create reservation with id", e);
        }
    }

    public void update(Reservation reservation){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RESERVATION, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setDate(1, Date.valueOf(reservation.getStartDate()));
            statement.setDate(2, Date.valueOf(reservation.getEndDate()));
            statement.setLong(3, reservation.getLodgerId());
            statement.setLong(4, reservation.getRoomId());
            statement.setLong(5, reservation.getId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not update reservation", e);
        }
    }

    public List<Reservation> findAll(){
        List<Reservation> reservations = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_RESERVATIONS)) {
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
            reservation.setId(resultSet.getLong(RESERVATION_ID));
            reservation.setStartDate(resultSet.getDate(RESERVATION_START_DATE).toLocalDate());
            reservation.setEndDate(resultSet.getDate(RESERVATION_END_DATE).toLocalDate());
            reservation.setLodgerId(resultSet.getLong(RESERVATION_LODGER_ID));
            reservation.setRoomId(resultSet.getLong(RESERVATION_ROOM_ID));
        } catch (SQLException ex){
            throw new DAOException("Can not parse reservation from resultSet");
        }
        return reservation;
    }
}
