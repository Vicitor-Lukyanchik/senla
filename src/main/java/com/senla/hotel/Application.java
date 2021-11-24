package com.senla.hotel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.formatter.HotelFormatter;
import com.senla.hotel.formatter.HotelFormatterImpl;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.service.impl.RoomServiceImpl;
import com.senla.hotel.service.impl.ServiceServiceImpl;
import com.senla.hotel.sorter.RoomsSorterImpl;
import com.senla.hotel.sorter.ServiceSorterImpl;

public class Application {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        LodgerService lodgerService = new LodgerServiceImpl(hotel);
        ServiceService serviceService = new ServiceServiceImpl(hotel);
        RoomService roomService = new RoomServiceImpl(hotel);
        HotelFormatter hotelFormatter = new HotelFormatterImpl();      
        RoomsSorterImpl roomSorter = new RoomsSorterImpl();
        ServiceSorterImpl serviceSorter = new ServiceSorterImpl();        
        
        //Создание номеров
        roomService.create(new Room(101, new BigDecimal(10), 1, 2, false, false));
        roomService.create(new Room(102, new BigDecimal(10), 1, 2, false, false));
        roomService.create(new Room(103, new BigDecimal(20), 2, 3, false, false));
        roomService.create(new Room(104, new BigDecimal(32), 4, 3, false, false));
        roomService.create(new Room(105, new BigDecimal(100), 2, 5, false, false));
        System.out.println(hotelFormatter.formatRooms(hotel.getRooms()));
        
        //Создание Клиентов
        lodgerService.create(new Lodger("Victor", "Conors", "6344224"));
        lodgerService.create(new Lodger("Valera", "Parker", "3218715"));
        lodgerService.create(new Lodger("Maksim", "Steicy", "2873582"));
        lodgerService.create(new Lodger("John", "Osborn", "2394639"));
        System.out.println(hotelFormatter.formatLodgers(hotel.getLodgers()));
        
        //Создание Услуги
        serviceService.create(new Service("Tidy", new BigDecimal(1)));
        serviceService.create(new Service("Clean", new BigDecimal(2)));
        System.out.println(hotelFormatter.formatServices(hotel.getServices()));
        
        //Обновить статус комнаты и изменить стоимость конаты
        roomService.updateStatus(1);
        roomService.updateCost(1, new BigDecimal(15));
        System.out.println(hotelFormatter.formatRooms(hotel.getRooms()));
        
        //Изменить стоимость сервиса
        serviceService.updateCost(1, new BigDecimal(3));
        System.out.println(hotelFormatter.formatServices(hotel.getServices()));
        
        //Поселить клиент
        lodgerService.createReservation(new Reservation(LocalDate.of(2021, 5, 1),LocalDate.of(2021, 5, 6), 1, 1));
        lodgerService.createReservation(new Reservation(LocalDate.of(2021, 5, 1),LocalDate.of(2021, 5, 4), 2, 2));
        lodgerService.createReservation(new Reservation(LocalDate.of(2021, 5, 1),LocalDate.of(2021, 5, 3), 3, 3));
        lodgerService.createReservation(new Reservation(LocalDate.of(2021, 5, 1),LocalDate.of(2021, 5, 8), 4, 5));
        System.out.println(hotelFormatter.formatLodgerReversations(hotel.getReservations()));
        
        //Выселить клиента
        roomService.updateNotSettle(1);
        System.out.println(hotelFormatter.formatRooms(hotel.getRooms()));
        
        //Список номеров (сортировки)
        System.out.println(hotelFormatter.formatRooms(roomSorter.sortRoomsByStars(roomService.findAll())));
        System.out.println(hotelFormatter.formatRooms(roomSorter.sortRoomsByCapacity(roomService.findAll())));
        System.out.println(hotelFormatter.formatRooms(roomSorter.sortRoomsByCost(roomService.findAll())));
        
        //Список сурвисов (сортировка)
        System.out.println(hotelFormatter.formatServices(serviceSorter.sortRoomsByCost(serviceService.findAll())));

        //Список не заселенных номеров
        System.out.println(hotelFormatter.formatRooms(roomService.findAllNotSettled()));

        //Сумма которую должен оплатить за номер клиент
        System.out.println(hotelFormatter.formatLodgerReversationsCost(lodgerService.findReservationCostByLodgerId(2)));
        
        //Детали номера
        System.out.println(hotelFormatter.formatRooms(Arrays.asList(roomService.find(1))));
        
        //Список свободных номеров в определенный день
        System.out.println(hotelFormatter.formatRooms(roomService.findAllNotSettledOnDate(LocalDate.of(2021, 5, 6))));
        
        //Общее число свободных номеров
        System.out.println(roomService.findAllNotSettled().size() + "\n");
        
        //Общее число клиентов
        System.out.println(lodgerService.findAll().size());
        
        //Клиенты и места проживания
        System.out.println(hotelFormatter.formatLodgersRooms(lodgerService.findAllLodgersRooms()));
    }
}
