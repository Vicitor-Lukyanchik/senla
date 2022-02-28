package com.senla.hotel.service.connection;

import com.senla.hotel.annotation.ConfigProperty;
import com.senla.hotel.annotation.Log;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionProvider {

    @Log
    private Logger log;
    @ConfigProperty(propertyName = "database.url", type = "string")
    private String url;
    @ConfigProperty(propertyName = "database.username", type = "string")
    private String username;
    @ConfigProperty(propertyName = "database.password", type = "string")
    private String password;


    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            String message = "Can not get connection";
            log.error(message);
            throw new DAOException(message, e);
        }
    }
}
