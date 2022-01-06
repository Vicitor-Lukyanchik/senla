package com.senla.hotel.ui.itembuilder.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.ConsoleReader;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;
import com.senla.hotel.ui.itembuilder.LodgerItemsBuilder;

public class LodgerItemsBuilderImpl implements LodgerItemsBuilder {

    private static LodgerItemsBuilder instance;

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();

    private Integer commandNumber = 1;
    private LodgerService lodgerService;

    public LodgerItemsBuilderImpl() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    public static LodgerItemsBuilder getInstance() {
        if (instance == null) {
            instance = new LodgerItemsBuilderImpl();
        }
        return instance;
    }

    public Map<Integer, MenuItem> buildLodgerItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new HashMap<>();
        result.put(commandNumber++, createMenuItem("Add lodger", addLodger(), rootMenu));
        result.put(commandNumber++, createMenuItem("Evict lodger from room", evictLodger(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find count lodgers", findCountLodgers(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find lodgers services", findLodgersServices(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find lodgers rooms", findLodgersRooms(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find reservation cost", findReservationCost(), rootMenu));
        result.put(commandNumber++, createMenuItem("Order service to lodger", orderServiceToLodger(), rootMenu));
        result.put(commandNumber++, createMenuItem("Put lodger in room", putLodgerInRoom(), rootMenu));
        result.put(commandNumber++, createMenuItem("Import lodger", importLodger(), rootMenu));
        result.put(commandNumber++, createMenuItem("Export lodger", exportLodger(), rootMenu));
        result.put(commandNumber++, createMenuItem("Import reservation", importReservation(), rootMenu));
        result.put(commandNumber++, createMenuItem("Export reservation", exportReservation(), rootMenu));
        result.put(commandNumber++, createMenuItem("Import service order", importServiceOrder(), rootMenu));
        result.put(commandNumber++, createMenuItem("Export service order", exportServiceOrder(), rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    private Action addLodger() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input room id : ");
            Long roomId = ConsoleReader.readLong();
            lodgerService.updateReservationIsReserved(lodgerId, roomId);
        };
    }

    private Action evictLodger() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input room id : ");
            Long roomId = ConsoleReader.readLong();
            lodgerService.updateReservationIsReserved(lodgerId, roomId);
        };
    }

    private Action findCountLodgers() {
        return () -> System.out.println("\nCount lodgers : " + lodgerService.findAll().size());
    }

    private Action findLodgersServices() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.println(hotelFormatter.formatLodgerServices(lodgerService.findServiceOrderByLodgerId(lodgerId)));
        };
    }

    private Action findLodgersRooms() {
        return () -> {
            Map<Lodger, Room> lodgersRooms = lodgerService.findAllNowLodgersRooms();
            System.out.println(hotelFormatter.formatLodgersRooms(lodgersRooms));
        };
    }

    private Action findReservationCost() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.println(
                    hotelFormatter.formatLodgerReversationsCost(lodgerService.findReservationCostByLodgerId(lodgerId)));
        };
    }

    private Action orderServiceToLodger() {
        return () -> {
            ConsoleReader.readLine();
            System.out.print("\nInput date : ");
            LocalDate endDate = ConsoleReader.readDate();
            System.out.print("Input lodger id : ");
            Long lodgerId = ConsoleReader.readLong();
            System.out.print("Input service id : ");
            Long serviceId = ConsoleReader.readLong();
            lodgerService.createServiceOrder(endDate, lodgerId, serviceId);
        };
    }

    private Action putLodgerInRoom() {
        return () -> {
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
        };
    }

    private Action importLodger() {
        return () -> lodgerService.importLodgers();
    }

    private Action exportLodger() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long id = ConsoleReader.readLong();
            lodgerService.exportLodger(id);
        };
    }

    private Action importReservation() {
        return () -> lodgerService.importReservations();
    }

    private Action exportReservation() {
        return () -> {
            System.out.print("\nInput reservation id : ");
            Long id = ConsoleReader.readLong();
            lodgerService.exportReservation(id);
        };
    }

    private Action importServiceOrder() {
        return () -> lodgerService.importServiceOrders();
    }

    private Action exportServiceOrder() {
        return () -> {
            System.out.print("\nInput service order id : ");
            Long id = ConsoleReader.readLong();
            lodgerService.exportServiceOrder(id);
        };
    }
}
