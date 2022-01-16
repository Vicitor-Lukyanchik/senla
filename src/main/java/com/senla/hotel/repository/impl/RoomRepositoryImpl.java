package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Room;
import com.senla.hotel.repository.RoomRepository;

@Singleton
public class RoomRepositoryImpl implements RoomRepository {

    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
