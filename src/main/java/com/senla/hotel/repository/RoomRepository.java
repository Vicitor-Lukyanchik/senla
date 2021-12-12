package com.senla.hotel.repository;

import java.util.List;

import com.senla.hotel.domain.Room;

public interface RoomRepository {

    List<Room> getRooms();

    void addRoom(Room room);
}
