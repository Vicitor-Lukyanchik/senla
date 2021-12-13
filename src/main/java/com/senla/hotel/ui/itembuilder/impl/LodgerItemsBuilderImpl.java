package com.senla.hotel.ui.itembuilder.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.filerepository.LodgerFileRepository;
import com.senla.hotel.filerepository.ReservationFileRepository;
import com.senla.hotel.filerepository.ServiceOrderFileRepository;
import com.senla.hotel.filerepository.impl.LodgerFileRepositoryImpl;
import com.senla.hotel.filerepository.impl.ReservationFileRepositoryImpl;
import com.senla.hotel.filerepository.impl.ServiceOrderFileRepositoryImpl;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;
import com.senla.hotel.ui.Action;
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
    private LodgerFileRepository lodgerFile;
    private ReservationFileRepository reservationFile;
    private ServiceOrderFileRepository serviceOrderFile;

    public LodgerItemsBuilderImpl() {
        lodgerService = LodgerServiceImpl.getInstance();
        lodgerFile = LodgerFileRepositoryImpl.getInstance();
        reservationFile = ReservationFileRepositoryImpl.getInstance();
        serviceOrderFile = ServiceOrderFileRepositoryImpl.getInstance();
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
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long id = ConsoleReader.readLong();
            Lodger importLodger = lodgerFile.findById(id);
            try {
                Lodger lodger = lodgerService.findById(id);
                lodger.setFirstName(importLodger.getFirstName());
                lodger.setLastName(importLodger.getLastName());
                lodger.setPhoneNumber(importLodger.getPhoneNumber());
            } catch (ServiceException ex) {
                lodgerService.createWithId(id, importLodger.getFirstName(), importLodger.getLastName(),
                        importLodger.getPhoneNumber());
            }
        };
    }

    private Action exportLodger() {
        return () -> {
            System.out.print("\nInput lodger id : ");
            Long id = ConsoleReader.readLong();
            Lodger exportLodger = lodgerService.findById(id);
            lodgerFile.export(exportLodger);
        };
    }

    private Action importReservation() {
        return () -> {
            System.out.print("\nInput reservation id : ");
            Long id = ConsoleReader.readLong();
            Reservation importReservation = reservationFile.findById(id);
            try {
                Reservation reservation = lodgerService.findReservationById(id);
                reservation.setStartDate(importReservation.getStartDate());
                reservation.setEndDate(importReservation.getEndDate());
                reservation.setLodgerId(importReservation.getLodgerId());
                reservation.setRoomId(importReservation.getRoomId());
            } catch (ServiceException ex) {
                lodgerService.createReservationWithId(id, importReservation.getStartDate(),
                        importReservation.getEndDate(), importReservation.getLodgerId(), importReservation.getRoomId());
            }
        };
    }

    private Action exportReservation() {
        return () -> {
            System.out.print("\nInput reservation id : ");
            Long id = ConsoleReader.readLong();
            Reservation exportReservation = lodgerService.findReservationById(id);
            reservationFile.export(exportReservation);
        };
    }

    private Action importServiceOrder() {
        return () -> {
            System.out.print("\nInput service order id : ");
            Long id = ConsoleReader.readLong();
            ServiceOrder importReservation = serviceOrderFile.findById(id);
            try {
                ServiceOrder reservation = lodgerService.findServiceOrderById(id);
                reservation.setDate(importReservation.getDate());
                reservation.setLodgerId(importReservation.getLodgerId());
                reservation.setServiceId(importReservation.getServiceId());
            } catch (ServiceException ex) {
                lodgerService.createServiceOrderWithId(id, importReservation.getDate(), importReservation.getLodgerId(),
                        importReservation.getServiceId());
            }
        };
    }

    private Action exportServiceOrder() {
        return () -> {
            System.out.print("\nInput service order id : ");
            Long id = ConsoleReader.readLong();
            ServiceOrder exportServiceOrder = lodgerService.findServiceOrderById(id);
            serviceOrderFile.export(exportServiceOrder);
        };
    }
}
