package com.senla.hotel.service.connection;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Log;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class Transaction {

    @Log
    private Logger log;
    @InjectByType
    private ConnectionPool connectionPool;
    private Connection connection;

    public Connection getConnection() {
        if (connection == null){
            String message = "Connection can not be null";
            log.error(message);
            throw new DAOException(message);
        }
        return connection;
    }

    public void begin() {
        connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {

            String message = "Can not begin connection";
            log.error(message);
            throw new DAOException(message, e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            String message = "Can not commit in db";
            log.error(message);
            throw new DAOException(message, e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException | NullPointerException e) {
            String message = "Can not rollback connection";
            log.error(message);
            throw new DAOException(message, e);
        }
    }

    public void end() {
        try {
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
            connection = null;
        } catch (SQLException | NullPointerException e) {
            String message = "Can not end connection";
            log.error(message);
            throw new DAOException(message, e);
        }
    }
}
