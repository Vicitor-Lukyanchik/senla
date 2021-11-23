package com.senla.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;

public interface RoomService {

    void create(Room room);
    
    void updateSettle(Integer id);
    
    void updateNotSettle(Integer id);
    
    void updateStatus(Integer id);
    
    void updateCost(Integer id, BigDecimal cost);
    
    Room find(Integer id);
    
    List<Room> findAll();
    
    List<Room> findAllNotSettled();
    
    List<Room> findAllNotSettledOnDate(LocalDate date);
    
    Hotel getHotel();
}
