package task4.domain;

import java.util.LinkedList;
import java.util.List;

public class Hotel {

    private List<Room> rooms = new LinkedList<>();
    private List<Service> services = new LinkedList<>();

    public boolean addRoom(Room room) {
        validateRoom(room);
        return rooms.add(room);
    }

    public boolean deleteRoom(Room room) {
        validateRoom(room);
        return rooms.remove(room);
    }

    private void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room can not be null");
        }
    }

    public void addService(Service service) {
        validateService(service);
        services.add(service);
    }

    public boolean deleteService(Service service) {
        validateService(service);
        return services.remove(service);
    }

    private void validateService(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service can not be null");
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Service> getServices() {
        return services;
    }
}
