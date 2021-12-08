package com.senla.hotel.ui.itembuilder.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.service.impl.RoomServiceImpl;
import com.senla.hotel.sorter.RoomsSorter;
import com.senla.hotel.sorter.impl.RoomsSorterImpl;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;
import com.senla.hotel.ui.itembuilder.RoomItemsBuilder;

public class RoomItemsBuilderImpl implements RoomItemsBuilder {

    private static final boolean DEFAULT_REPAIRED = false;
    private static final LocalDate DATE_NOW = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));

    private static RoomItemsBuilder instance;

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();
    private final RoomsSorter roomsSorter = new RoomsSorterImpl();

    private Integer commandNumber = 1;
    private RoomService roomService;
    private LodgerService lodgerService;

    public RoomItemsBuilderImpl() {
        roomService = RoomServiceImpl.getInstance();
        lodgerService = LodgerServiceImpl.getInstance();
    }

    public static RoomItemsBuilder getInstance() {
        if (instance == null) {
            instance = new RoomItemsBuilderImpl();
        }
        return instance;
    }

    public Map<Integer, MenuItem> buildRoomItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new HashMap<>();

        result.put(commandNumber++, createMenuItem("Add room", addRoom(), rootMenu));
        result.put(commandNumber++, createMenuItem("Change room cost", changeRoomCost(), rootMenu));
        result.put(commandNumber++, createMenuItem("Change room status", changeRoomStatus(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find count not settled rooms", findCountNotSettledRooms(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find last lodgers, who lived in room", findLastLodgers(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find not settled rooms", findNotSettledRooms(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find not settled rooms on date", findNotSettledRoomsOnDate(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find room", findRoom(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms", findRooms(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms costs", findRoomsCosts(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by capacity", sortRoomsByCapacity(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by cost", sortRoomsByCost(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by stars", sortRoomsByStars(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort not settled rooms by capacity", sortNotSettledRoomsByCapacity(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort not settled rooms by cost", sortNotSettledRoomsByCost(), rootMenu));
        result.put(commandNumber++, createMenuItem("Sort not settled rooms by stars", sortNotSettledRoomsByStars(), rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    private Action addRoom() {
        return () -> {
            System.out.print("\nInput room number : ");
            int number = ConsoleReader.readNumber();
            System.out.print("Input room capacity : ");
            int capacity = ConsoleReader.readNumber();
            System.out.print("Input room stars count : ");
            int stars = ConsoleReader.readNumber();
            System.out.print("Input room cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            roomService.create(number, cost, capacity, stars, DEFAULT_REPAIRED);
        };
    }

    private Action changeRoomCost() {
        return () -> {
            System.out.print("\nInput room number : ");
            Long id = ConsoleReader.readLong();
            System.out.print("Input room cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            roomService.updateCost(id, cost);
        };
    }

    private Action changeRoomStatus() {
        return () -> {
            System.out.print("\nInput room id : ");
            Long id = ConsoleReader.readLong();
            roomService.updateStatus(id);
        };
    }

    private Action findCountNotSettledRooms() {
        return () -> System.out
                .println("\nCount not settled rooms : " + lodgerService.findAllNotSettledRoomOnDate(DATE_NOW).size());
    }

    private Action findLastLodgers() {
        return () -> {
            System.out.print("\nInput room id : ");
            Long roomId = ConsoleReader.readLong();
            System.out.print("\nInput how many last lodgers : ");
            Integer limit = ConsoleReader.readNumber();
            Map<LocalDate, Lodger> reservations = lodgerService.findLastReservationsByRoomId(roomId, limit);
            System.out.println(hotelFormatter.formatLastRoomReservations(reservations));
        };
    }
    
    private Action findNotSettledRooms() {
        return () -> {
            List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(DATE_NOW);
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action findNotSettledRoomsOnDate() {
        return () -> {
            ConsoleReader.readLine();
            System.out.print("\nInput date : ");
            List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(ConsoleReader.readDate());
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action findRoom() {
        return () -> {
            System.out.print("\nInput room id : ");
            Long id = ConsoleReader.readLong();
            Room room = roomService.findById(id);
            System.out.println(hotelFormatter.formatRooms(Arrays.asList(room)));
        };
    }
    
    private Action findRooms() {
        return () -> {
            List<Room> rooms = roomService.findAll();
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action findRoomsCosts() {
        return () -> {
            List<Room> rooms = roomService.findAll();
            System.out.println(hotelFormatter.formatRoomsCosts(rooms));
        };
    }
    
    private Action sortRoomsByCapacity() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByCapacity(roomService.findAll());
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action sortRoomsByCost() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByCost(roomService.findAll());
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action sortRoomsByStars() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByStars(roomService.findAll());
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action sortNotSettledRoomsByCapacity() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByCapacity(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action sortNotSettledRoomsByCost() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByCost(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }
    
    private Action sortNotSettledRoomsByStars() {
        return () -> {
            List<Room> rooms = roomsSorter.sortRoomsByStars(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
            System.out.println(hotelFormatter.formatRooms(rooms));
        };
    }  
}
