package com.senla.hotel.dao;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection openConnection();
}
