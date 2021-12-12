package com.senla.hotel.sorter.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.senla.hotel.domain.Room;
import com.senla.hotel.sorter.RoomsSorter;

public class RoomsSorterImpl implements RoomsSorter {

    public List<Room> sortRoomsByCost(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getCost)).collect(Collectors.toList());
    }

    public List<Room> sortRoomsByStars(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getStars)).collect(Collectors.toList());
    }

    public List<Room> sortRoomsByCapacity(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getCapacity)).collect(Collectors.toList());
    }
}
