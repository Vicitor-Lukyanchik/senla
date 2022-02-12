package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class LodgerDaoImpl implements LodgerDao {

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(String firstName, String lastName, String phone){
        String sql = "INSERT INTO lodgers (id, firstName, last_name, phone_number) VALUES (nextval('lodgers_id_seq'), ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phone);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create lodger", e);
        }
    }
    public void createWithId(Long id, String firstName, String lastName, String phone){
        String sql = "INSERT INTO lodgers (id, firstName, last_name, phone_number) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, phone);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create lodger with id", e);
        }
    }

    public List<Lodger> findAll(){
        String sql = "SELECT l.id, l.first_name, l.last_name, l.phone_number FROM lodgers l";
        List<Lodger> lodgers = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lodgers.add(buildLodger(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find lodgers", e);
        }
        return lodgers;
    }

    private Lodger buildLodger(ResultSet resultSet) throws SQLException {
        Lodger lodger = new Lodger();
        lodger.setId(resultSet.getLong("id"));
        lodger.setFirstName(resultSet.getString("first_name"));
        lodger.setLastName(resultSet.getString("last_name"));
        lodger.setPhoneNumber(resultSet.getString("phone_number"));
        return lodger;
    }
}
