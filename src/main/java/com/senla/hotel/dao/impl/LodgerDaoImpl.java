package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Singleton
public class LodgerDaoImpl implements LodgerDao {

    private static final String LODGER_TABLE = "lodgers";
    private static final String LODGER_SEQUENCE = "nextval('lodgers_id_seq')";
    private static final String INSERT_LODGER = "INSERT INTO " + LODGER_TABLE +
            "(" + LODGER_ID + "," + LODGER_FIRST_NAME + "," + LODGER_LAST_NAME + "," + LODGER_PHONE + ") " +
            "VALUES (" + LODGER_SEQUENCE + ", ?, ?, ?)";

    private static final String INSERT_LODGER_WITH_ID = "INSERT INTO " + LODGER_TABLE +
            "(" + LODGER_ID + ", " + LODGER_FIRST_NAME + ", " + LODGER_LAST_NAME + ", " + LODGER_PHONE + ") " +
            "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_LODGER = "UPDATE " + LODGER_TABLE +
            " SET " + LODGER_FIRST_NAME + " = ?, " + LODGER_LAST_NAME + " = ?, " + LODGER_PHONE + " = ? " +
            "WHERE " + LODGER_ID + " = ?";

    private static final String SELECT_LODGERS = "SELECT " + LODGER_ID + ", " + LODGER_FIRST_NAME + ", " +
            LODGER_LAST_NAME + ", " + LODGER_PHONE + " FROM " + LODGER_TABLE;

    public void create(Lodger lodger, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_LODGER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lodger.getFirstName());
            statement.setString(2, lodger.getLastName());
            statement.setString(3, lodger.getPhoneNumber());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create lodger", e);
        }
    }

    public void createWithId(Lodger lodger, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_LODGER_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, lodger.getId());
            statement.setString(2, lodger.getFirstName());
            statement.setString(3, lodger.getLastName());
            statement.setString(4, lodger.getPhoneNumber());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create lodger with id", e);
        }
    }

    public void update(Lodger lodger, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LODGER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, lodger.getFirstName());
            statement.setString(2, lodger.getLastName());
            statement.setString(3, lodger.getPhoneNumber());
            statement.setLong(4, lodger.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update lodger", e);
        }
    }

    public List<Lodger> findAll(Connection connection) {
        List<Lodger> lodgers = new LinkedList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_LODGERS)) {
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

    private Lodger buildLodger(ResultSet resultSet) {
        Lodger lodger = new Lodger();
        try {
            lodger.setId(resultSet.getLong(LODGER_ID));
            lodger.setFirstName(resultSet.getString(LODGER_FIRST_NAME));
            lodger.setLastName(resultSet.getString(LODGER_LAST_NAME));
            lodger.setPhoneNumber(resultSet.getString(LODGER_PHONE));
        } catch (SQLException ex) {
            throw new DAOException("Can not parse lodger from resultSet");
        }
        return lodger;
    }
}
