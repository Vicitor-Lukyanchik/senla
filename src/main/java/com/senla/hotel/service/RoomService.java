package com.senla.hotel.service;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Room;

public interface RoomService {

    void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired);

    void updateStatus(Long id);

    void updateCost(Long id, BigDecimal cost);

    Room find(Long id);

    List<Room> findAll();
}
