package com.senla.hotel.service.connection;

import com.senla.hotel.annotation.*;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ConnectionPool {

    @Log
    private Logger log;
    @InjectByType
    private ConnectionProvider connectionProvider;
    @ConfigProperty(propertyName = "pool.size")
    private int poolSize;
    private List<Connection> usedConnections = new ArrayList<>();
    private List<Connection> pool;

    @PostConstruct
    private void create() {
        pool = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool.add(createConnection());
        }
        log.info("Create database connection pool");
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
