package com.senla.hotel.repository;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Room;

public class RoomRepositoryImpl implements RoomRepository {

    private static RoomRepository instance;
    private List<Room> rooms = new ArrayList<>();

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepositoryImpl();
        }
        return instance;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
