package com.senla.hotel.service;

import com.senla.hotel.domain.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {
    void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired);

    void importRooms();

    void exportRoom(Long id);

    void updateCost(Long id, BigDecimal cost);

    Room findById(Long id);

    List<Room> findAll();
}