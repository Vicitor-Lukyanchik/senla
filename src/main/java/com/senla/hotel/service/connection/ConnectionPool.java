package com.senla.hotel.service.connection;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConnectionPool {

    private static final int INITIAL_POOL_SIZE = 10;

    @InjectByType
    private ConnectionProvider connectionProvider;
    private List<Connection> pool;
    private List<Connection> usedConnections = new ArrayList<>();

    public void create() {
        pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
    }

    private Connection createConnection() {
        return connectionProvider.getConnection();
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
