package com.senla.hotel.dao.connection;

import com.senla.hotel.annotation.ConfigProperty;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConnectionPool {

    private static final int INITIAL_POOL_SIZE = 10;

    @ConfigProperty(propertyName = "database.url", type = "string")
    private String url;
    @ConfigProperty(propertyName = "database.username", type = "string")
    private String username;
    @ConfigProperty(propertyName = "database.password", type = "string")
    private String password;
    private List<Connection> pool;
    private List<Connection> usedConnections = new ArrayList<>();

    public void create() {
        pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, username, password));
        }
    }

    private static Connection createConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DAOException("Can not get connection", e);
        }
    }

    public Connection getConnection() {
        Connection connection = pool.remove(pool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        pool.add(connection);
        return usedConnections.remove(connection);
    }
}
