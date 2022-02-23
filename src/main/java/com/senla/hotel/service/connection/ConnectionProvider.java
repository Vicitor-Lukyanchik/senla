package com.senla.hotel.service.connection;

import com.senla.hotel.annotation.ConfigProperty;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class ConnectionProvider {

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
            throw new DAOException("Can not get connection", e);
        }
    }
}
