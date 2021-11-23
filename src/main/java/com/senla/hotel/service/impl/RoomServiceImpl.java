package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Room;
import com.senla.hotel.service.RoomService;

public class RoomServiceImpl implements RoomService {

    private Hotel hotel;
    private Integer id = 1;

    public RoomServiceImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void create(Room room) {
        validateRoom(room);
        room.setId(id);
        id++;
        hotel.addRoom(room);
    }
    
    private void validateRoom(Room room) {
        if(room == null) {
            throw new IllegalArgumentException("Room can not be null");
        }
        if(room.getStars() < 1 || room.getStars() > 5) {
            throw new IllegalArgumentException("Star can not be less then 1 and more than 5");
        }
    }
    
    @Override
    public void updateSettle(Integer id) {
        Room room = find(id);
        if (!(room.isSettled() || room.isRepaired())) {
            room.setSettled(true);
        } else {
            System.out.println("This room is settled or repaired");
        }
    }

    @Override
    public void updateNotSettle(Integer id) {
        Room room = find(id);
        if (room.isSettled()) {
            room.setSettled(false);
        } else {
            System.out.println("This room is not settled");
        }
    }

    @Override
    public void updateStatus(Integer id) {
        Room room = find(id);
        room.setRepaired(!room.isRepaired());
    }

    @Override
    public void updateCost(Integer id, BigDecimal cost) {
        Room room = find(id);
        room.setCost(cost);
    }

    @Override
    public Room find(Integer id) {
        for(Room room : hotel.getRooms()) {
            if(room.getId().equals(id)) {
                return room;
            }
        }
        throw new IllegalArgumentException("There is not room with this number");
    }
    
    @Override
    public List<Room> findAll() {
        return hotel.getRooms();
    }
    
    @Override
    public List<Room> findAllNotSettled() {
        return hotel.getNotSettledRooms();
    }
    
    @Override
    public List<Room> findAllNotSettledOnDate(LocalDate date) {
        return hotel.getNotSettledRoomsOnDate(date);
    }

    @Override
    public Hotel getHotel() {
        return hotel;
    }
}
