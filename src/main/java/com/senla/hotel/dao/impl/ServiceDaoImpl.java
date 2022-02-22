package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.senla.hotel.dao.TableColumns.*;

@Singleton
public class ServiceDaoImpl implements ServiceDao {

    private static final String SERVICE_TABLE = "services";
    private static final String SERVICE_SEQUENCE = "nextval('services_id_seq')";
    private static final String INSERT_SERVICE = "INSERT INTO" + SERVICE_TABLE +
            "(" + SERVICE_ID +"," + SERVICE_NAME + "," + SERVICE_COST + ") " +
            "VALUES (" + SERVICE_SEQUENCE + ", ?, ?)";

    private static final String INSERT_SERVICE_WITH_ID = "INSERT INTO" + SERVICE_TABLE +
            "(" + SERVICE_ID +", " + SERVICE_NAME + ", " + SERVICE_COST + ") " +
            "VALUES (?, ?, ?)";

    private static final String UPDATE_SERVICE = "UPDATE " + SERVICE_TABLE +
            " SET " + SERVICE_NAME + " = ?, " + SERVICE_COST + " = ? " +
            "WHERE " + SERVICE_ID + " = ?";

    private static final String SELECT_SERVICES = "SELECT " + SERVICE_ID + ", " + SERVICE_NAME + ", " + SERVICE_COST 
            + " FROM " + SERVICE_TABLE;

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(Service service){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, service.getName());
            statement.setBigDecimal(2, service.getCost());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create service", e);
        }
    }

    public void createWithId(Service service){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SERVICE_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setLong(1, service.getId());
            statement.setString(2, service.getName());
            statement.setBigDecimal(3, service.getCost());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create service with id", e);
        }
    }

    public void update(Service service){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SERVICE, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, service.getName());
            statement.setBigDecimal(2, service.getCost());
            statement.setLong(3, service.getId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not update service", e);
        }
    }

    public List<Service> findAll(){
        List<Service> services = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SERVICES)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    services.add(buildService(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find services", e);
        }
        return services;
    }

    private Service buildService(ResultSet resultSet) {
        Service service = new Service();
        try {
            service.setId(resultSet.getLong(SERVICE_ID));
            service.setName(resultSet.getString(SERVICE_NAME));
            service.setCost(resultSet.getBigDecimal(SERVICE_COST));
        } catch (SQLException ex){
            throw new DAOException("Can not parse service from resultSet");
        }
        return service;
    }
}
