package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class ServiceOrderDaoImpl implements ServiceOrderDao {

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(LocalDate date, Long lodgerId, Long serviceId){
        String sql = "INSERT INTO service_orders (id, date, lodger_id, service_id) " +
                "VALUES (nextval('service_orders_id_seq'), ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setLong(3, lodgerId);
            statement.setLong(4, serviceId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create service order", e);
        }
    }

    public void createWithId(Long id, LocalDate date, Long lodgerId, Long serviceId){
        String sql = "INSERT INTO service_orders (id, date, lodger_id, service_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.setDate(2, Date.valueOf(date));
            statement.setLong(4, lodgerId);
            statement.setLong(5, serviceId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create service order with id", e);
        }
    }

    public void update(Long id, LocalDate date, Long lodgerId, Long serviceId){
        String sql = "UPDATE service_orders SET date= ?, lodger_id = ?, service_id = ?" +
                "WHERE id = ?";

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setLong(2, lodgerId);
            statement.setLong(3, serviceId);
            statement.setLong(4, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not update service order", e);
        }
    }

    public List<ServiceOrder> findAll(){
        String sql = "SELECT s.id, s.date, s.lodger_id, s.service_id FROM service_orders s";
        List<ServiceOrder> serviceOrders = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    serviceOrders.add(buildServiceOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find service orders", e);
        }
        return serviceOrders;
    }

    private ServiceOrder buildServiceOrder(ResultSet resultSet) {
        ServiceOrder serviceOrder = new ServiceOrder();
        try {
            serviceOrder.setId(resultSet.getLong("id"));
            serviceOrder.setDate(resultSet.getDate("date").toLocalDate());
            serviceOrder.setLodgerId(resultSet.getLong("lodger_id"));
            serviceOrder.setServiceId(resultSet.getLong("service_id"));
        } catch (SQLException ex){
            throw new DAOException("Can not parse service order from resultSet");
        }
        return serviceOrder;
    }
}
