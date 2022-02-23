package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Singleton
public class RoomDaoImpl implements RoomDao {

    private static final String ROOM_TABLE = "rooms";
    private static final String ROOM_SEQUENCE = "nextval('rooms_id_seq')";
    private static final String INSERT_ROOM = "INSERT INTO " + ROOM_TABLE +
            "(" + ROOM_ID + "," + ROOM_NUMBER + "," + ROOM_COST + "," + ROOM_CAPACITY +
            "," + ROOM_STARS + "," + ROOM_REPAIRED + ") " +
            "VALUES (" + ROOM_SEQUENCE + ", ?, ?, ?, ?, ?)";

    private static final String INSERT_ROOM_WITH_ID = "INSERT INTO " + ROOM_TABLE +
            "(" + ROOM_ID + ", " + ROOM_NUMBER + ", " + ROOM_COST + ", " + ROOM_CAPACITY +
            ", " + ROOM_STARS + ", " + ROOM_REPAIRED + ") " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_ROOM = "UPDATE " + ROOM_TABLE +
            " SET " + ROOM_NUMBER + " = ?, " + ROOM_COST + " = ?, " + ROOM_CAPACITY +
            " = ?, " + ROOM_STARS + " = ?, " + ROOM_REPAIRED + " = ? " +
            "WHERE " + ROOM_ID + " = ?";

    private static final String SELECT_ROOMS = "SELECT " + ROOM_ID + ", " + ROOM_NUMBER + ", " + ROOM_COST +
            ", " + ROOM_CAPACITY + ", " + ROOM_STARS + ", " + ROOM_REPAIRED + " FROM " + ROOM_TABLE;

    public void create(Room room, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, room.getNumber());
            statement.setBigDecimal(2, room.getCost());
            statement.setInt(3, room.getCapacity());
            statement.setInt(4, room.getStars());
            statement.setBoolean(5, room.isRepaired());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create room", e);
        }
    }

    public void createWithId(Room room, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, room.getId());
            statement.setInt(2, room.getNumber());
            statement.setBigDecimal(3, room.getCost());
            statement.setInt(4, room.getCapacity());
            statement.setInt(5, room.getStars());
            statement.setBoolean(6, room.isRepaired());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create room with id", e);
        }
    }

    public void update(Room room, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROOM, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, room.getNumber());
            statement.setBigDecimal(2, room.getCost());
            statement.setInt(3, room.getCapacity());
            statement.setInt(4, room.getStars());
            statement.setBoolean(5, room.isRepaired());
            statement.setLong(6, room.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update room", e);
        }
    }

    public List<Room> findAll(Connection connection) {
        List<Room> rooms = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROOMS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(buildRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find rooms", e);
        }
        return rooms;
    }

    private Room buildRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        try {
            room.setId(resultSet.getLong(ROOM_ID));
            room.setNumber(resultSet.getInt(ROOM_NUMBER));
            room.setCost(resultSet.getBigDecimal(ROOM_COST));
            room.setCapacity(resultSet.getInt(ROOM_CAPACITY));
            room.setStars(resultSet.getInt(ROOM_STARS));
            room.setRepaired(resultSet.getBoolean(ROOM_REPAIRED));
        } catch (SQLException ex) {
            throw new DAOException("Can not parse room from resultSet");
        }
        return room;
    }
}
