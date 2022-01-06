package com.senla.hotel.ui.itembuilder.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.senla.hotel.annotation.Off;
import com.senla.hotel.context.ApplicationContext;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.ConsoleReader;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.itembuilder.RoomItemsBuilder;

public class RoomItemsBuilderImpl implements RoomItemsBuilder {

    private static final boolean DEFAULT_REPAIRED = false;
    private static final LocalDate DATE_NOW = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));

    private final HotelFormatter hotelFormatter = ApplicationContext.getInstance().getObject(HotelFormatter.class);

    private Integer commandNumber = 1;
    private RoomService roomService = ApplicationContext.getInstance().getObject(RoomService.class);
    private LodgerService lodgerService = ApplicationContext.getInstance().getObject(LodgerService.class);

    public Map<Integer, MenuItem> buildRoomItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new LinkedHashMap<>();

        result.put(commandNumber++, createMenuItem("Add room", addRoom, rootMenu));
        result.put(commandNumber++, createMenuItem("Change room cost", changeRoomCost, rootMenu));
        result.put(commandNumber++, createMenuItem("Change room status", changeRoomStatus, rootMenu));
        result.put(commandNumber++, createMenuItem("Find count not settled rooms", findCountNotSettledRooms, rootMenu));
        result.put(commandNumber++, createMenuItem("Find last lodgers, who lived in room", findLastLodgers, rootMenu));
        result.put(commandNumber++, createMenuItem("Find not settled rooms", findNotSettledRooms, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Find not settled rooms on date", findNotSettledRoomsOnDate, rootMenu));
        result.put(commandNumber++, createMenuItem("Find room", findRoom, rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms", findRooms, rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms costs", findRoomsCosts, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by capacity", sortRoomsByCapacity, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by cost", sortRoomsByCost, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by stars", sortRoomsByStars, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by capacity", sortNotSettledRoomsByCapacity, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by cost", sortNotSettledRoomsByCost, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by stars", sortNotSettledRoomsByStars, rootMenu));
        result.put(commandNumber++, createMenuItem("Import rooms", importRoom, rootMenu));
        result.put(commandNumber++, createMenuItem("Export room", exportRoom, rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    @Off
    private Action addRoom = () -> {
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

    private Action changeRoomCost = () -> {
        System.out.print("\nInput room id : ");
        Long id = ConsoleReader.readLong();
        System.out.print("Input new room cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();
        roomService.updateCost(id, cost);
    };

    private Action changeRoomStatus = () -> {
        System.out.print("\nInput room id : ");
        Long id = ConsoleReader.readLong();
        roomService.updateStatus(id);
    };

    private Action findCountNotSettledRooms = () -> System.out
            .println("\nCount not settled rooms : " + lodgerService.findAllNotSettledRoomOnDate(DATE_NOW).size());

    private Action findLastLodgers = () -> {
        System.out.print("\nInput room id : ");
        Long roomId = ConsoleReader.readLong();
        System.out.print("\nInput how many last lodgers : ");
        Integer limit = ConsoleReader.readNumber();
        Map<LocalDate, Lodger> reservations = lodgerService.findLastReservationsByRoomId(roomId, limit);
        System.out.println(hotelFormatter.formatLastRoomReservations(reservations));
    };

    private Action findNotSettledRooms = () -> {
        List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(DATE_NOW);
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action findNotSettledRoomsOnDate = () -> {
        ConsoleReader.readLine();
        System.out.print("\nInput date : ");
        List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(ConsoleReader.readDate());
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action findRoom = () -> {
        System.out.print("\nInput room id : ");
        Long id = ConsoleReader.readLong();
        Room room = roomService.findById(id);
        System.out.println(hotelFormatter.formatRooms(Arrays.asList(room)));
    };

    private Action findRooms = () -> {
        List<Room> rooms = roomService.findAll();
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action findRoomsCosts = () -> {
        List<Room> rooms = roomService.findAll();
        System.out.println(hotelFormatter.formatRoomsCosts(rooms));
    };

    private Action sortRoomsByCapacity = () -> {
        List<Room> rooms = sortRoomsByCapacity(roomService.findAll());
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortRoomsByCost = () -> {
        List<Room> rooms = sortRoomsByCost(roomService.findAll());
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortRoomsByStars = () -> {
        List<Room> rooms = sortRoomsByStars(roomService.findAll());
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortNotSettledRoomsByCapacity = () -> {
        List<Room> rooms = sortRoomsByCapacity(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortNotSettledRoomsByCost = () -> {
        List<Room> rooms = sortRoomsByCost(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortNotSettledRoomsByStars = () -> {
        List<Room> rooms = sortRoomsByStars(lodgerService.findAllNotSettledRoomOnDate(DATE_NOW));
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    public List<Room> sortRoomsByCost(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getCost)).collect(Collectors.toList());
    }

    public List<Room> sortRoomsByStars(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getStars)).collect(Collectors.toList());
    }

    public List<Room> sortRoomsByCapacity(List<Room> rooms) {
        return rooms.stream().sorted(Comparator.comparing(Room::getCapacity)).collect(Collectors.toList());
    }

    private Action importRoom = () -> roomService.importRooms();

    private Action exportRoom = () -> {
        System.out.print("\nInput room id : ");
        Long id = ConsoleReader.readLong();
        roomService.exportRoom(id);
    };
}
