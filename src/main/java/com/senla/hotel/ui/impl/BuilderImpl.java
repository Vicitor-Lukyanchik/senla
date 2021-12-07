package com.senla.hotel.ui.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.service.impl.RoomServiceImpl;
import com.senla.hotel.service.impl.ServiceServiceImpl;
import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;

public class BuilderImpl implements Builder {

    private static final boolean DEFAULT_REPAIRED = false;
    private static final LocalDate DATE = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));

    private static Builder instance;

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();

    private Menu rootMenu = null;
    private Menu roomMenu = new Menu();
    private Menu serviceMenu = new Menu();
    private Menu lodgerMenu = new Menu();

    private RoomService roomService;
    private ServiceService serviceService;
    private LodgerService lodgerService;

    public BuilderImpl() {
        roomService = RoomServiceImpl.getInstance();
        serviceService = ServiceServiceImpl.getInstance();
        lodgerService = LodgerServiceImpl.getInstance();
    }

    public static Builder getInstance() {
        if (instance == null) {
            instance = new BuilderImpl();
        }
        return instance;
    }

    private void buildMenu() {
        buildRootMenu();
        buildRoomMenu();
        buildServiceMenu();
        buildLodgerMenu();
    }

    private void buildRootMenu() {
        rootMenu = new Menu();
        rootMenu.setName("Root menu");
        Map<Integer, MenuItem> rootMenuItems = new HashMap<>();
        rootMenuItems.put(1, new MenuItem("Commands with rooms", roomMenu));
        rootMenuItems.get(1).setAction(() -> {
        });
        rootMenuItems.put(2, new MenuItem("Commands with services", serviceMenu));
        rootMenuItems.get(2).setAction(() -> {
        });
        rootMenuItems.put(3, new MenuItem("Commands with lodgers", lodgerMenu));
        rootMenuItems.get(3).setAction(() -> {
        });
        rootMenuItems.put(4, new MenuItem("Exit", null));
        rootMenuItems.get(4).setAction(() -> {
        });
        rootMenu.setMenuItems(rootMenuItems);
    }

    private void buildRoomMenu() {
        roomMenu.setName("Room menu");
        roomMenu.setMenuItems(buildRoomItems());
    }

    private Map<Integer, MenuItem> buildRoomItems() {
        Map<Integer, MenuItem> result = new HashMap<>();

        result.put(1, new MenuItem("Add room", rootMenu));
        result.get(1).setAction(() -> {
            System.out.print("\nInput room number : ");
            int number = ConsoleReader.readNumber();
            System.out.print("Input room capacity : ");
            int capacity = ConsoleReader.readNumber();
            System.out.print("Input room stars count : ");
            int stars = ConsoleReader.readNumber();
            System.out.print("Input room cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            roomService.create(number, cost, capacity, stars, DEFAULT_REPAIRED);
        });

        result.put(2, new MenuItem("Change room cost", rootMenu));
        result.get(2).setAction(() -> {
            System.out.print("\nInput room number : ");
            Long id = ConsoleReader.readLong();
            System.out.print("Input room cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            roomService.updateCost(id, cost);
        });

        result.put(3, new MenuItem("Change room status", rootMenu));
        result.get(3).setAction(() -> {
            System.out.print("\nInput room id : ");
            Long id = ConsoleReader.readLong();
            roomService.updateStatus(id);
        });

        result.put(4, new MenuItem("Find count not settled rooms", rootMenu));
        result.get(4).setAction(() -> {
            System.out.println("\nCount not settled rooms : " + lodgerService.findAllNotSettledRoomOnDate(DATE).size());
        });

        result.put(5, new MenuItem("Find last lodgers, who lived in room", rootMenu));
        result.get(5).setAction(() -> {
            System.out.print("\nInput room id : ");
            Long roomId = ConsoleReader.readLong();
            System.out.print("\nInput how many last reservations : ");
            Integer limit = ConsoleReader.readNumber();
            Map<LocalDate, Lodger> reservations = lodgerService.findLastReservationsByRoomId(roomId, limit);
            System.out.println(hotelFormatter.formatLastRoomReservations(reservations));
        });

        result.put(6, new MenuItem("Find not settled rooms", rootMenu));
        result.get(6).setAction(() -> {
            List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(DATE);
            System.out.println(hotelFormatter.formatRooms(rooms));
        });

        result.put(7, new MenuItem("Find  not settled rooms on date", rootMenu));
        result.get(7).setAction(() -> {
            ConsoleReader.readLine();
            System.out.print("\nInput date : ");
            List<Room> rooms = lodgerService.findAllNotSettledRoomOnDate(ConsoleReader.readDate());
            System.out.println(hotelFormatter.formatRooms(rooms));
        });

        result.put(8, new MenuItem("Find room", rootMenu));
        result.get(8).setAction(() -> {
            System.out.print("\nInput room id : ");
            Long id = ConsoleReader.readLong();
            Room room = roomService.findById(id);
            System.out.println(hotelFormatter.formatRooms(Arrays.asList(room)));
        });

        result.put(9, new MenuItem("Find rooms", rootMenu));
        result.get(9).setAction(() -> {
            List<Room> rooms = roomService.findAll();
            System.out.println(hotelFormatter.formatRooms(rooms));
        });

        result.put(10, new MenuItem("Find rooms costs", rootMenu));
        result.get(10).setAction(() -> {
            List<Room> rooms = roomService.findAll();
            System.out.println(hotelFormatter.formatRoomsCosts(rooms));
        });

        return result;
    }

    private void buildServiceMenu() {
        serviceMenu.setName("Service menu");
        serviceMenu.setMenuItems(buildServiceItems());
    }

    private Map<Integer, MenuItem> buildServiceItems() {
        Map<Integer, MenuItem> result = new HashMap<>();
        result.put(1, new MenuItem("Add service", rootMenu));
        result.get(1).setAction(() -> {
            System.out.print("\nInput service name : ");
            ConsoleReader.readLine();
            String name = ConsoleReader.readLine();
            System.out.print("Input service cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            serviceService.create(name, cost);
        });

        result.put(2, new MenuItem("Change service cost", rootMenu));
        result.get(2).setAction(() -> {
            List<Service> services = serviceService.findAll();
            System.out.println(hotelFormatter.formatServicesCosts(services));
        });

        result.put(3, new MenuItem("Find services", rootMenu));
        result.get(3).setAction(() -> {
            System.out.println(hotelFormatter.formatServices(serviceService.findAll()));
        });

        result.put(4, new MenuItem("Find services cost", rootMenu));
        result.get(4).setAction(() -> {
            List<Service> services = serviceService.findAll();
            System.out.println(hotelFormatter.formatServicesCosts(services));
        });

        return result;
    }

    private void buildLodgerMenu() {
        lodgerMenu.setName("Lodger menu");
        lodgerMenu.setMenuItems(buildLodgerItems());
    }

    private Map<Integer, MenuItem> buildLodgerItems() {
        Map<Integer, MenuItem> result = new HashMap<>();
        result.put(1, new MenuItem("Add lodger", rootMenu));
        result.get(1).setAction(() -> {
            System.out.print("\nInput lodger first-name : ");
            ConsoleReader.readLine();
            String firstName = ConsoleReader.readLine();
            System.out.print("Input lodger last-name : ");
            String lastName = ConsoleReader.readLine();
            System.out.print("Input lodger phone number : ");
            String phone = ConsoleReader.readLine();
            lodgerService.create(firstName, lastName, phone);
        });

        result.put(2, new MenuItem("Evict lodger from room", rootMenu));
        result.get(2).setAction(() -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input room id : ");
            Long roomId = ConsoleReader.readLong();
            lodgerService.updateReservationIsReserved(lodgerId, roomId);
        });

        result.put(3, new MenuItem("Find count lodgers", rootMenu));
        result.get(3).setAction(() -> {
            System.out.println("\nCount lodgers : " + lodgerService.findAll().size());
        });

        result.put(4, new MenuItem("Find lodgers services", rootMenu));
        result.get(4).setAction(() -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.println(hotelFormatter.formatLodgerServices(lodgerService.findServiceOrderByLodgerId(lodgerId)));
        });

        result.put(5, new MenuItem("Find lodgers rooms", rootMenu));
        result.get(5).setAction(() -> {
            Map<Lodger, Room> lodgersRooms = lodgerService.findAllNowLodgersRooms();
            System.out.println(hotelFormatter.formatLodgersRooms(lodgersRooms));
        });

        result.put(6, new MenuItem("Find reservation cost", rootMenu));
        result.get(6).setAction(() -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.println(
                    hotelFormatter.formatLodgerReversationsCost(lodgerService.findReservationCostByLodgerId(lodgerId)));
        });

        result.put(7, new MenuItem("Order service to lodger", rootMenu));
        result.get(7).setAction(() -> {
            ConsoleReader.readLine();
            System.out.print("\nInput date : ");
            LocalDate endDate = ConsoleReader.readDate();
            System.out.print("Input lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input service id : ");
            Long serviceId = ConsoleReader.readLong();
            lodgerService.createSeviceOrder(endDate, lodgerId, serviceId);
        });

        result.put(8, new MenuItem("Put lodger in room", rootMenu));
        result.get(8).setAction(() -> {
            ConsoleReader.readLine();
            System.out.print("\nInput start date : ");
            LocalDate startDate = ConsoleReader.readDate();
            System.out.print("Input end date : ");
            LocalDate endDate = ConsoleReader.readDate();
            System.out.print("Input lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input room id : ");
            Long roomId = ConsoleReader.readLong();
            lodgerService.createReservation(startDate, endDate, lodgerId, roomId);
        });

        return result;
    }

    @Override
    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
