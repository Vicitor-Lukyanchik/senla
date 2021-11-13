package task4.adminiter;

import task4.domain.Hotel;
import task4.domain.Room;
import task4.domain.Service;

public class HotelAdministerImpl implements HotelAdminister {

    private Hotel hotel = new Hotel();

    @Override
    public void putInRoom(Room room) {
        Room chosenRoom = findRoom(room);
        if (!(chosenRoom.isSettled() || chosenRoom.isRepaired())) {
            chosenRoom.setSettled(true);
        } else {
            System.out.println("This room is settled or repaired");
        }
    }

    @Override
    public void evictFromRoom(Room room) {
        Room chosenRoom = findRoom(room);
        if (chosenRoom.isSettled()) {
            chosenRoom.setSettled(false);
        } else {
            System.out.println("This room is not settled");
        }
    }

    @Override
    public void changeRoomStatus(Room room) {
        Room chosenRoom = findRoom(room);
        chosenRoom.setRepaired(!chosenRoom.isRepaired());
    }

    @Override
    public void changeRoomCost(Room room, int cost) {
        validateCost(cost);
        Room chosenRoom = findRoom(room);
        chosenRoom.setCost(cost);
    }

    private Room findRoom(Room room) {
        if (!hotel.getRooms().contains(room)) {
            throw new IllegalArgumentException("This room does not exist");
        }
        int index = hotel.getRooms().indexOf(room);
        return hotel.getRooms().get(index);
    }

    @Override
    public void addRoom(Room room) {
        hotel.addRoom(room);
    }

    @Override
    public void changeServiceCost(Service service, int cost) {
        validateCost(cost);
        Service chosenService = findService(service);
        chosenService.setCost(cost);
    }

    private void validateCost(int cost) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost can not be less than zero");
        }
    }

    private Service findService(Service service) {
        if (!hotel.getServices().contains(service)) {
            throw new IllegalArgumentException("This service does not exist");
        }
        int index = hotel.getServices().indexOf(service);
        return hotel.getServices().get(index);
    }

    @Override
    public void addService(Service service) {
        hotel.addService(service);
    }

    public Hotel getHotel() {
        return hotel;
    }
}
