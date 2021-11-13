package task4.adminiter;

import task4.domain.Room;
import task4.domain.Service;

public interface HotelAdminister {
    void putInRoom(Room room);

    void evictFromRoom(Room room);

    void changeRoomStatus(Room room);

    void changeRoomCost(Room room, int cost);

    void addRoom(Room room);

    void changeServiceCost(Service service, int cost);

    void addService(Service service);
}
