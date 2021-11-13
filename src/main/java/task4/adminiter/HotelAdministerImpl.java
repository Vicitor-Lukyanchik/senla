package task4.adminiter;

import task4.domain.Hotel;
import task4.domain.Room;
import task4.domain.Service;

public class HotelAdministerImpl implements HotelAdminister {

    private Hotel hotel = new Hotel();

    @Override
    public void putInRoom(Room room) {
        int index = findRoom(room);
        Room chosenRoom = hotel.getRooms().get(index);
        if (!(chosenRoom.isSettled() || chosenRoom.isRepaired())) {
            chosenRoom.setSettled(true);
        } else {
            System.out.println("This room is settled or repaired");
        }
    }

    @Override
    public void evictFromRoom(Room room) {
        int index = findRoom(room);
        if (hotel.getRooms().get(index).isSettled()) {
            hotel.getRooms().get(index).setSettled(false);
        } else {
            System.out.println("This room is not settled");
        }
    }

    @Override
    public void changeRoomStatus(Room room) {
        int index = findRoom(room);
        Room chosenRoom = hotel.getRooms().get(index);
        hotel.getRooms().get(index).setRepaired(!chosenRoom.isRepaired());
    }

    @Override
    public void changeRoomCost(Room room, int cost) {
        validateCost(cost);
        int index = findRoom(room);
        hotel.getRooms().get(index).setCost(cost);
    }

    private int findRoom(Room room) {
        if (!hotel.getRooms().contains(room)) {
            throw new IllegalArgumentException("This room does not exist");
        }
        return hotel.getRooms().indexOf(room);
    }

    @Override
    public void addRoom(Room room) {
        hotel.addRoom(room);
    }

    @Override
    public void changeServiceCost(Service service, int cost) {
        validateCost(cost);
        int index = findService(service);
        hotel.getServices().get(index).setCost(cost);
    }

    private void validateCost(int cost) {
        if (cost <= 0) {
            throw new IllegalArgumentException("Cost can not be less than zero");
        }
    }

    private int findService(Service service) {
        if (!hotel.getServices().contains(service)) {
            throw new IllegalArgumentException("This service does not exist");
        }
        return hotel.getServices().indexOf(service);
    }

    @Override
    public void addService(Service service) {
        hotel.addService(service);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Rooms: \n");
        for (int i = 0; i < hotel.getRooms().size(); i++) {
            Room room = hotel.getRooms().get(i);
            result.append(i + 1).append(" ").append(room.getCost()).append(" ").append(room.isSettled()).append(" ")
                    .append(room.isRepaired()).append("\n");
        }
        result.append("Services: \n");
        for (int i = 0; i < hotel.getServices().size(); i++) {
            Service service = hotel.getServices().get(i);
            result.append(i + 1).append(" ").append(service.getName()).append(" ").append(service.getCost())
                    .append("\n");
        }
        return result.toString();
    }
}
