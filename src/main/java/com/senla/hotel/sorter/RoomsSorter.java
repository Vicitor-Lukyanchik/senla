package com.senla.hotel.sorter;

import java.util.List;

import com.senla.hotel.domain.Room;

public interface RoomsSorter {
    List<Room> sortRoomsByCost(List<Room> rooms);
    
    List<Room> sortRoomsByStars(List<Room> rooms);
    
    List<Room> sortRoomsByCapacity(List<Room> rooms);
}
