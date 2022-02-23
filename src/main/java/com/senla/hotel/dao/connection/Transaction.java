package com.senla.hotel.dao.connection;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class Transaction {

    @InjectByType
    private ConnectionPool connectionPool;
    private Connection connection;

    public Connection getConnection() {
        if (connection == null){
            throw new DAOException("Connection can not be null");
        }
        return connection;
    }

    public void begin() {
        connection = connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DAOException("Can not begin connection", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException | NullPointerException e) {
            throw new DAOException("Can not commit in db", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException | NullPointerException e) {
            throw new DAOException("Can not rollback connection", e);
        }
    }

    public void end() {
        try {
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
            connection = null;
        } catch (SQLException | NullPointerException e) {
            throw new DAOException("Can not end connection", e);
        }
    }
}
