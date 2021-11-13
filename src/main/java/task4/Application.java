package task4;

import task4.adminiter.HotelAdminister;
import task4.adminiter.HotelAdministerImpl;
import task4.domain.Room;
import task4.domain.Service;

public class Application {

    public static void main(String[] args) {
        HotelAdminister hotelAdminister = new HotelAdministerImpl();
        Room room1 = new Room(10);
        Room room2 = new Room(20);
        Room room3 = new Room(30);
        Room room4 = new Room(40);
        Room room5 = new Room(50);
        Service service1 = new Service("Cleaning", 1);
        Service service2 = new Service("Food", 2);

        hotelAdminister.addRoom(room1);
        hotelAdminister.addRoom(room2);
        hotelAdminister.addRoom(room3);
        hotelAdminister.addRoom(room4);
        hotelAdminister.addRoom(room5);
        hotelAdminister.addService(service1);
        hotelAdminister.addService(service2);

        System.out.println(hotelAdminister);

        hotelAdminister.putInRoom(room5);
        hotelAdminister.changeRoomCost(room1, 20);
        hotelAdminister.changeRoomStatus(room3);
        System.out.println(hotelAdminister);

        hotelAdminister.evictFromRoom(room5);
        hotelAdminister.changeServiceCost(service1, 3);
        System.out.println(hotelAdminister);
    }
}
