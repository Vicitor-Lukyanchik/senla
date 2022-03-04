package com.senla.hotel.parser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;

@Singleton
public class CsvParserImpl implements CsvParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<Room> parseRooms(List<String> rooms) {
        List<Room> result = new ArrayList<>();
        for (String room : rooms) {
            result.add(parseRoom(room.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Room parseRoom(String[] room) {
        Long id = Long.parseLong(room[0]);
        int number = Integer.parseInt(room[1]);
        Validator.validatePrice(room[2]);
        BigDecimal cost = new BigDecimal(room[2]);
        int capacity = Integer.parseInt(room[3]);
        int stars = Integer.parseInt(room[4]);
        return new Room(id, number, cost, capacity, stars);
    }

    @Override
    public List<String> parseRoomsToLines(List<Room> rooms) {
        List<String> result = new ArrayList<>();
        for (Room room : rooms) {
            result.add(parseLine(room));
        }
        return result;
    }

    private String parseLine(Room room) {
        return room.getId() + NEXT_COLUMN + room.getNumber() + NEXT_COLUMN + room.getCost() + NEXT_COLUMN
                + room.getCapacity() + NEXT_COLUMN + room.getStars();
    }

    @Override
    public List<Lodger> parseLodgers(List<String> lodgers) {
        List<Lodger> result = new ArrayList<>();
        for (String lodger : lodgers) {
            result.add(parseLodger(lodger.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Lodger parseLodger(String[] lodger) {
        Long id = Long.parseLong(lodger[0]);
        String firstName = lodger[1];
        String lastName = lodger[2];
        Validator.validatePhone(lodger[3]);
        String phone = lodger[3];
        return new Lodger(id, firstName, lastName, phone);
    }

    @Override
    public List<String> parseLodgersToLines(List<Lodger> lodgers) {
        List<String> result = new ArrayList<>();
        for (Lodger lodger : lodgers) {
            result.add(parseLodgerLine(lodger));
        }
        return result;
    }

    private String parseLodgerLine(Lodger lodger) {
        return lodger.getId() + NEXT_COLUMN + lodger.getFirstName() + NEXT_COLUMN + lodger.getLastName() + NEXT_COLUMN
                + lodger.getPhoneNumber();
    }

    @Override
    public List<Reservation> parseReservations(List<String> reservations) {
        List<Reservation> result = new ArrayList<>();
        for (String reservation : reservations) {
            result.add(parseReservation(reservation.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Reservation parseReservation(String[] reservation) {
        Long id = Long.parseLong(reservation[0]);
        Validator.validateDate(reservation[1]);
        LocalDate startDate = LocalDate.parse(reservation[1], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Validator.validateDate(reservation[2]);
        LocalDate endDate = LocalDate.parse(reservation[2], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long lodgerId = Long.parseLong(reservation[3]);
        Long roomId = Long.parseLong(reservation[4]);
        Boolean reserved = reservation[5].equals("true");
        return new Reservation(id, startDate, endDate, lodgerId, roomId, reserved);
    }

    @Override
    public List<String> parseReservationsToLines(List<Reservation> reservations) {
        List<String> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            result.add(parseReservationLine(reservation));
        }
        return result;
    }

    private String parseReservationLine(Reservation reservation) {
        String isRepaired = "false";
        if (reservation.isReserved()) {
            isRepaired = "true";
        }
        return reservation.getId() + NEXT_COLUMN + reservation.getStartDate() + NEXT_COLUMN + reservation.getEndDate()
                + reservation.getLodgerId() + NEXT_COLUMN + reservation.getRoomId() + NEXT_COLUMN;
    }

    @Override
    public List<ServiceOrder> parseServiceOrders(List<String> serviceOrders) {
        List<ServiceOrder> result = new ArrayList<>();
        for (String serviceOrder : serviceOrders) {
            result.add(parseServiceOrder(serviceOrder.split(NEXT_COLUMN)));
        }
        return result;
    }

    private ServiceOrder parseServiceOrder(String[] serviceOrder) {
        Long id = Long.parseLong(serviceOrder[0]);
        Validator.validateDate(serviceOrder[1]);
        LocalDate date = LocalDate.parse(serviceOrder[1], DateTimeFormatter.ofPattern("d.MM.yyyy"));
        Long lodgerId = Long.parseLong(serviceOrder[2]);
        Long serviceId = Long.parseLong(serviceOrder[3]);
        return new ServiceOrder(id, date, lodgerId, serviceId);
    }

    @Override
    public List<String> parseServiceOrdersToLines(List<ServiceOrder> serviceOrders) {
        List<String> result = new ArrayList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(parseLine(serviceOrder));
        }
        return result;
    }

    private String parseLine(ServiceOrder serviceOrder) {
        return serviceOrder.getId() + NEXT_COLUMN + serviceOrder.getDate() + NEXT_COLUMN + serviceOrder.getLodgerId()
                + NEXT_COLUMN + serviceOrder.getServiceId();
    }

    @Override
    public List<Service> parseServices(List<String> services) {
        List<Service> result = new ArrayList<>();
        for (String service : services) {
            result.add(parseService(service.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Service parseService(String[] service) {
        Long id = Long.parseLong(service[0]);
        String name = service[1];
        Validator.validatePrice(service[2]);
        BigDecimal cost = new BigDecimal(service[2]);
        return new Service(id, name, cost);
    }

    @Override
    public List<String> parseServicesToLines(List<Service> services) {
        List<String> result = new ArrayList<>();
        for (Service service : services) {
            result.add(parseLine(service));
        }
        return result;
    }

    private String parseLine(Service service) {
        return service.getId() + NEXT_COLUMN + service.getName() + NEXT_COLUMN + service.getCost();
    }
}
