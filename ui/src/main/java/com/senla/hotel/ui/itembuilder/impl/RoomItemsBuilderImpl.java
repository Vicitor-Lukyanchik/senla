package com.senla.hotel.ui.itembuilder.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class RoomItemsBuilderImpl implements RoomItemsBuilder {

    private static final boolean DEFAULT_REPAIRED = false;
    private static final String DATE_PATTERN = "d.MM.yyyy";

    @Autowired
    private HotelFormatter hotelFormatter;
    @Autowired
    private RoomService roomService;
    @Autowired
    private LodgerService lodgerService;
    @Value("${date.now}")
    private String date_now;
    private LocalDate localDateNow;
    @Value("${RoomItemsBuilderImpl.limitRoomLodgers}")
    private int limitRoomLodgers;
    private Integer commandNumber = 1;

    @PostConstruct
    private void parseDate() {
        localDateNow = LocalDate.parse(date_now, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public Map<Integer, MenuItem> buildRoomItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new LinkedHashMap<>();

        result.put(commandNumber++, createMenuItem("Add room", addRoom, rootMenu));
        result.put(commandNumber++, createMenuItem("Change room cost", changeRoomCost, rootMenu));

        result.put(commandNumber++, createMenuItem("Find room", findRoom, rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms", findRooms, rootMenu));
        result.put(commandNumber++, createMenuItem("Find rooms costs", findRoomsCosts, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by capacity", sortRoomsByCapacity, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by cost", sortRoomsByCost, rootMenu));
        result.put(commandNumber++, createMenuItem("Sort rooms by stars", sortRoomsByStars, rootMenu));
        result.put(commandNumber++, createMenuItem("Find last lodgers, who lived in room", findLastLodgers, rootMenu));

        result.put(commandNumber++, createMenuItem("Find count not settled rooms", findCountNotSettledRooms, rootMenu));
        result.put(commandNumber++, createMenuItem("Find not settled rooms", findNotSettledRooms, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Find not settled rooms on date", findNotSettledRoomsOnDate, rootMenu));

        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by capacity", sortNotSettledRoomsByCapacity, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by cost", sortNotSettledRoomsByCost, rootMenu));
        result.put(commandNumber++,
                createMenuItem("Sort not settled rooms by stars", sortNotSettledRoomsByStars, rootMenu));

        result.put(commandNumber++, createMenuItem("Import rooms", importRooms, rootMenu));
        result.put(commandNumber++, createMenuItem("Export room", exportRoom, rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

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

    private Action findCountNotSettledRooms = () -> System.out
            .println("\nCount not settled rooms : " + lodgerService.findAllNotSettledRoomOnDate(localDateNow).size());

    private Action findLastLodgers = () -> {
        System.out.print("\nInput room id : ");
        Long roomId = ConsoleReader.readLong();
        Map<LocalDate, Lodger> reservations = lodgerService.findLastReservationsByRoomId(roomId, limitRoomLodgers);
        System.out.println(hotelFormatter.formatLastRoomReservations(reservations));
    };

    private Action findNotSettledRooms = () -> {
        List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(localDateNow);
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
        List<Room> rooms = sortRoomsByCapacity(lodgerService.findAllNotSettledRoomOnDate(localDateNow));
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortNotSettledRoomsByCost = () -> {
        List<Room> rooms = sortRoomsByCost(lodgerService.findAllNotSettledRoomOnDate(localDateNow));
        System.out.println(hotelFormatter.formatRooms(rooms));
    };

    private Action sortNotSettledRoomsByStars = () -> {
        List<Room> rooms = sortRoomsByStars(lodgerService.findAllNotSettledRoomOnDate(localDateNow));
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

    private Action importRooms = () -> roomService.importRooms();

    private Action exportRoom = () -> {
        System.out.print("\nInput room id : ");
        Long id = ConsoleReader.readLong();
        roomService.exportRoom(id);
    };
}
