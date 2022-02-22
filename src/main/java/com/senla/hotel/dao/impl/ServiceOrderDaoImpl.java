package com.senla.hotel.dao.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ConnectionProvider;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.DAOException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.senla.hotel.dao.TableColumns.*;

@Singleton
public class ServiceOrderDaoImpl implements ServiceOrderDao {

    private static final String SERVICE_ORDER_TABLE = "service_orders";
    private static final String SERVICE_ORDER_SEQUENCE = "nextval('service_orders_id_seq')";
    private static final String INSERT_SERVICE_ORDER = "INSERT INTO" + SERVICE_ORDER_TABLE +
            "(" + SERVICE_ORDER_ID +", " + SERVICE_ORDER_DATE +
            ", " + SERVICE_ORDER_LODGER_ID + ", " + SERVICE_ORDER_SERVICE_ID + ") " +
            "VALUES (" + SERVICE_ORDER_SEQUENCE + ", ?, ?, ?)";

    private static final String INSERT_SERVICE_ORDER_WITH_ID = "INSERT INTO" + SERVICE_ORDER_TABLE +
            "(" + SERVICE_ORDER_ID +"," + SERVICE_ORDER_DATE +
            "," + SERVICE_ORDER_LODGER_ID + "," + SERVICE_ORDER_SERVICE_ID + ") " +
            "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_SERVICE_ORDER = "UPDATE " + SERVICE_ORDER_TABLE +
            " SET " + SERVICE_ORDER_DATE + " = ?, "
            + SERVICE_ORDER_LODGER_ID + " = ?, " + SERVICE_ORDER_SERVICE_ID + " = ? " +
            "WHERE " + SERVICE_ORDER_ID + " = ?";

    private static final String SELECT_SERVICE_ORDERS = "SELECT " + SERVICE_ORDER_ID +", " + SERVICE_ORDER_DATE +
            ", " + SERVICE_ORDER_LODGER_ID + ", " + SERVICE_ORDER_SERVICE_ID + " FROM " + SERVICE_ORDER_TABLE;

    @InjectByType
    private ConnectionProvider connectionProvider;

    public void create(ServiceOrder serviceOrder){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SERVICE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setDate(1, Date.valueOf(serviceOrder.getDate()));
            statement.setLong(2, serviceOrder.getLodgerId());
            statement.setLong(3, serviceOrder.getServiceId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create service order", e);
        }
    }

    public void createWithId(ServiceOrder serviceOrder){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SERVICE_ORDER_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setLong(1, serviceOrder.getId());
            statement.setDate(2, Date.valueOf(serviceOrder.getDate()));
            statement.setLong(3, serviceOrder.getLodgerId());
            statement.setLong(4, serviceOrder.getServiceId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not create service order with id", e);
        }
    }

    public void update(ServiceOrder serviceOrder){
        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SERVICE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setDate(1, Date.valueOf(serviceOrder.getDate()));
            statement.setLong(2, serviceOrder.getLodgerId());
            statement.setLong(3, serviceOrder.getServiceId());
            statement.setLong(4, serviceOrder.getId());
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("Can not update service order", e);
        }
    }

    public List<ServiceOrder> findAll(){
        List<ServiceOrder> serviceOrders = new LinkedList<>();

        try (Connection connection = connectionProvider.openConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SERVICE_ORDERS)) {
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
            serviceOrder.setId(resultSet.getLong(SERVICE_ORDER_ID));
            serviceOrder.setDate(resultSet.getDate(SERVICE_ORDER_DATE).toLocalDate());
            serviceOrder.setLodgerId(resultSet.getLong(SERVICE_ORDER_LODGER_ID));
            serviceOrder.setServiceId(resultSet.getLong(SERVICE_ORDER_SERVICE_ID));
        } catch (SQLException ex){
            throw new DAOException("Can not parse service order from resultSet");
        }
        return serviceOrder;
    }
}
