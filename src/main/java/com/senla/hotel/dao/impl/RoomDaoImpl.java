package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class RoomDaoImpl implements RoomDao {

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired){
        String sql = "INSERT INTO rooms (id, number, cost, capacity, stars, repaired) VALUES (nextval('rooms_id_seq'), ?, ?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, number);
            statement.setBigDecimal(2, cost);
            statement.setInt(3, capacity);
            statement.setInt(4, stars);
            statement.setBoolean(5, isRepaired);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create room", e);
        }
    }

    public void createWithId(Long id, int number, BigDecimal cost, int capacity, int stars, boolean isRepaired){
        String sql = "INSERT INTO rooms (id, number, cost, capacity, stars, repaired) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setInt(2, number);
            statement.setBigDecimal(3, cost);
            statement.setInt(4, capacity);
            statement.setInt(5, stars);
            statement.setBoolean(6, isRepaired);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create room with id", e);
        }
    }

    public void update(Long id, int number, BigDecimal cost, int capacity, int stars, boolean isRepaired){
        String sql = "UPDATE rooms SET number= ? , cost = ?, capacity = ?, stars = ?, repaired = ? " +
                "WHERE id = ?";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, number);
            statement.setBigDecimal(2, cost);
            statement.setInt(3, capacity);
            statement.setInt(4, stars);
            statement.setBoolean(5, isRepaired);
            statement.setLong(6, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update room", e);
        }
    }

    public List<Room> findAll(){
        String sql = "SELECT r.id, r.number, r.cost, r.capacity, r.stars, r.repaired FROM rooms r";
        List<Room> rooms = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
            room.setId(resultSet.getLong("id"));
            room.setNumber(resultSet.getInt("number"));
            room.setCost(resultSet.getBigDecimal("cost"));
            room.setCapacity(resultSet.getInt("capacity"));
            room.setStars(resultSet.getInt("stars"));
            room.setRepaired(resultSet.getBoolean("repaired"));
        } catch (SQLException ex){
            throw new DAOException("Can not parse room from resultSet");
        }
        return room;
    }
}
