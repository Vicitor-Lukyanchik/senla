package com.senla.hotel.filerepository;

import com.senla.hotel.domain.Room;

public interface RoomFileRepository {
    Room findById(Long id);
    
    void export(Room room);
}
