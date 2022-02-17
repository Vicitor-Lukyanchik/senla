package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class ServiceDaoImpl implements ServiceDao {

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(String name, BigDecimal cost){
        String sql = "INSERT INTO services (id, name, cost) VALUES (nextval('services_id_seq'), ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setBigDecimal(2, cost);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create service", e);
        }
    }

    public void createWithId(Long id, String name, BigDecimal cost){
        String sql = "INSERT INTO Services (id, name, cost) VALUES (?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setString(2, name);
            statement.setBigDecimal(3, cost);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create service with id", e);
        }
    }

    public void update(Long id, String name, BigDecimal cost){
        String sql = "UPDATE services SET name = ?, cost = ? WHERE l.id = ?";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setBigDecimal(2, cost);
            statement.setLong(3, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update service", e);
        }
    }

    public List<Service> findAll(){
        String sql = "SELECT s.id, s.name, s.cost FROM services s";
        List<Service> services = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
            service.setId(resultSet.getLong("id"));
            service.setName(resultSet.getString("name"));
            service.setCost(resultSet.getBigDecimal("cost"));
        } catch (SQLException ex){
            throw new DAOException("Can not parse service from resultSet");
        }
        return service;
    }
}
