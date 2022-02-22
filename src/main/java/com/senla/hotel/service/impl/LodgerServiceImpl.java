package com.senla.hotel.service.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.*;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class LodgerServiceImpl implements LodgerService {

    private static final String LODGERS_PATH = "csv/lodgers.csv";
    private static final String RESERVATIONS_PATH = "csv/reservations.csv";
    private static final String SERVICE_ORDERS_PATH = "csv/service_orders.csv";

    @InjectByType
    private FileReader fileReader;
    @InjectByType
    private CsvParser csvParser;
    @InjectByType
    private FileWriter fileWriter;
    @InjectByType
    private ServiceService serviceService;
    @InjectByType
    private RoomService roomService;
    @InjectByType
    private LodgerDao lodgerDao;
    @InjectByType
    private ServiceOrderDao serviceOrderDao;
    @InjectByType
    private ReservationDao reservationDao;
    private List<Lodger> csvLodgers = new ArrayList<>();
    private List<Reservation> csvReservations = new ArrayList<>();
    private List<ServiceOrder> csvServiceOrders = new ArrayList<>();

    @Override
    public void create(String firstName, String lastName, String phone) {
        validateLodger(firstName, lastName, phone);
        lodgerDao.create(new Lodger(firstName, lastName, phone));
    }

    @Override
    public void importLodgers() {
        csvLodgers = getLodgersFromFile();
        for (Lodger importLodger : csvLodgers) {
            validateLodger(importLodger.getFirstName(), importLodger.getLastName(), importLodger.getPhoneNumber());
            try {
                findById(importLodger.getId());
                lodgerDao.update(new Lodger(importLodger.getId(), importLodger.getFirstName(), importLodger.getLastName(),
                        importLodger.getPhoneNumber()));
            } catch (ServiceException ex) {
                lodgerDao.createWithId(new Lodger(importLodger.getId(), importLodger.getFirstName(),
                        importLodger.getLastName(), importLodger.getPhoneNumber()));
            }
        }
    }

    private List<Lodger> getLodgersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(LODGERS_PATH);
        return csvParser.parseLodgers(lines);
    }

    private void validateLodger(String firstName, String lastName, String phone) {
        if (firstName.length() > 25 || lastName.length() > 25) {
            throw new ServiceException("First-last name length can not be more than 25");
        }
        if (firstName.length() < 2 || lastName.length() < 2) {
            throw new ServiceException("First-last name length can not be less than 2");
        }
        if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0))) {
            throw new ServiceException("First letter should be uppercase");
        }
        if (phone.length() != 7) {
            throw new ServiceException("Phone number length should be 7");
        }
    }

    @Override
    public void exportLodger(Long id) {
        Lodger lodger = findById(id);
        Lodger importLodger = csvLodgers.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        if (importLodger == null) {
            csvLodgers.add(lodger);
        } else {
            importLodger.setFirstName(lodger.getFirstName());
            importLodger.setLastName(lodger.getLastName());
            importLodger.setPhoneNumber(lodger.getPhoneNumber());
        }
        List<String> lines = csvParser.parseLodgersToLines(csvLodgers);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
    }

    @Override
    public List<Lodger> findAll() {
        return lodgerDao.findAll();
    }

    @Override
    public void createReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        validateReservation(startDate, endDate, lodgerId, roomId);
        reservationDao.create(new Reservation(startDate, endDate, lodgerId, roomId));
    }

    @Override
    public void importReservations() {
        csvReservations = getReservationsFromFile();
        for (Reservation importReservation : csvReservations) {
            validateReservation(importReservation.getStartDate(), importReservation.getEndDate(),
                    importReservation.getLodgerId(), importReservation.getRoomId());
            try {
                findReservationById(importReservation.getId());
                updateReservation(importReservation);
            } catch (ServiceException ex) {
                reservationDao.createWithId(new Reservation(importReservation.getId(),
                        importReservation.getStartDate(), importReservation.getEndDate(),
                        importReservation.getLodgerId(), importReservation.getRoomId()));
            }
        }
    }

    private List<Reservation> getReservationsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(RESERVATIONS_PATH);
        return csvParser.parseReservations(lines);
    }

    @Override
    public void exportReservation(Long id) {
        Reservation reservation = findReservationById(id);
        Reservation exportReservation = csvReservations.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElse(null);
        if (exportReservation == null) {
            csvReservations.add(reservation);
        } else {
            exportReservation.setLodgerId(reservation.getLodgerId());
            exportReservation.setRoomId(reservation.getRoomId());
            exportReservation.setStartDate(reservation.getStartDate());
            exportReservation.setEndDate(reservation.getEndDate());
        }
        List<String> lines = csvParser.parseLodgersToLines(csvLodgers);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
    }

    private void validateReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        findById(lodgerId);
        if (startDate.isAfter(endDate)) {
            throw new ServiceException("Start date can not be after than end date");
        }

        Room room = roomService.findById(roomId);
        List<Reservation> roomReservations = reservationDao.findAll().stream()
                .filter(r -> room.getId().equals(r.getRoomId())).collect(Collectors.toList());
        if (isRoomSettledOnDates(roomReservations, startDate, endDate)) {
            throw new ServiceException("Room is settled on this dates");
        }
    }

    private boolean isRoomSettledOnDates(List<Reservation> roomReservations, LocalDate startDate, LocalDate endDate) {
        if (roomReservations.isEmpty()) {
            return false;
        }
        for (Reservation reservation : roomReservations) {
            if (!(endDate.isBefore(reservation.getStartDate()) || startDate.isAfter(reservation.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateReservationReserved(Long lodgerId, Long roomId) {
        Reservation reservation = reservationDao.findAll().stream().filter(
                lodgerRoom -> roomId.equals(lodgerRoom.getRoomId()) && lodgerId.equals(lodgerRoom.getLodgerId()))
                .findFirst().orElseThrow(() -> new ServiceException("There is not room or lodger with this id"));

        if (reservation.isReserved()) {
            reservation.setReserved(false);
            updateReservation(reservation);
        } else {
            throw new ServiceException("This reservation is closed");
        }
    }

    private void updateReservation(Reservation reservation) {
        reservationDao.update(new Reservation(reservation.getId(),
                reservation.getStartDate(), reservation.getEndDate(),
                reservation.getLodgerId(), reservation.getRoomId()));
    }

    @Override
    public Map<Lodger, Room> findAllNowLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = reservationDao.findAll();

        for (Reservation reservation : reservations) {
            if (reservation.isReserved()) {
                Lodger lodger = findById(reservation.getLodgerId());
                Room room = roomService.findById(reservation.getRoomId());
                result.put(lodger, room);
            }
        }
        return result;
    }

    @Override
    public Map<LocalDate, Lodger> findLastReservationsByRoomId(Long roomId, int limit) {
        Map<LocalDate, Lodger> result = new LinkedHashMap<>();
        List<Reservation> reservations = reservationDao.findAll().stream()
                .filter(r -> roomId.equals(r.getRoomId()))
                .sorted(Comparator.comparing(Reservation::getStartDate).reversed()).limit(limit)
                .collect(Collectors.toList());

        for (Reservation reservation : reservations) {
            result.put(reservation.getStartDate(), findById(reservation.getLodgerId()));
        }
        return result;
    }

    @Override
    public Map<Integer, BigDecimal> findReservationCostByLodgerId(Long lodgerId) {
        findById(lodgerId);
        List<Reservation> reservations = reservationDao.findAll().stream()
                .filter(r -> lodgerId.equals(r.getLodgerId()) && r.isReserved()).collect(Collectors.toList());
        Map<Integer, BigDecimal> result = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            Room room = roomService.findById(reservation.getRoomId());
            Period period = Period.between(reservation.getStartDate(), reservation.getEndDate());
            BigDecimal cost = new BigDecimal(room.getCost().intValue() * period.getDays());
            result.put(room.getNumber(), cost);
        }
        return result;
    }

    @Override
    public List<Room> findAllNotSettledRoomOnDate(LocalDate date) {
        List<Reservation> reservations = reservationDao.findAll();
        List<Room> result = new LinkedList<>();

        for (Room room : roomService.findAll()) {
            List<Reservation> roomReservations = reservations.stream().filter(r -> room.getId().equals(r.getRoomId()))
                    .collect(Collectors.toList());
            if (isRoomNotSettledOnDate(roomReservations, date)) {
                result.add(room);
            }
        }
        return result;
    }

    private boolean isRoomNotSettledOnDate(List<Reservation> roomReservations, LocalDate date) {
        if (!roomReservations.isEmpty()) {
            for (Reservation reservation : roomReservations) {
                if (reservation.getStartDate().isBefore(date) && reservation.getEndDate().isAfter(date)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Reservation findReservationById(Long id) {
        for (Reservation reservation : reservationDao.findAll()) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        throw new ServiceException("There is not reservation with this id " + id);
    }

    @Override
    public void createServiceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        validateServiceOrder(lodgerId, serviceId);
        serviceOrderDao.create(new ServiceOrder(date, lodgerId, serviceId));
    }

    @Override
    public void importServiceOrders() {
        csvServiceOrders = getServiceOrdersFromFile();
        for (ServiceOrder importServiceOrder : csvServiceOrders) {
            validateServiceOrder(importServiceOrder.getLodgerId(), importServiceOrder.getServiceId());
            try {
                findServiceOrderById(importServiceOrder.getId());
                updateServiceOrder(importServiceOrder);
            } catch (ServiceException ex) {
                serviceOrderDao.createWithId(new ServiceOrder(importServiceOrder.getId(), importServiceOrder.getDate(),
                                importServiceOrder.getLodgerId(), importServiceOrder.getServiceId()));
            }
        }
    }

    private List<ServiceOrder> getServiceOrdersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(SERVICE_ORDERS_PATH);
        return csvParser.parseServiceOrders(lines);
    }

    private void updateServiceOrder(ServiceOrder importServiceOrder) {
        serviceOrderDao.update(new ServiceOrder(importServiceOrder.getId(), importServiceOrder.getDate(),
                importServiceOrder.getLodgerId(), importServiceOrder.getServiceId()));
    }

    @Override
    public void exportServiceOrder(Long id) {
        ServiceOrder serviceOrder = findServiceOrderById(id);
        ServiceOrder exportServiceOrder = csvServiceOrders.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElse(null);
        if (exportServiceOrder == null) {
            csvServiceOrders.add(serviceOrder);
        } else {
            exportServiceOrder.setLodgerId(serviceOrder.getLodgerId());
            exportServiceOrder.setServiceId(serviceOrder.getServiceId());
            exportServiceOrder.setDate(serviceOrder.getDate());
        }
        List<String> lines = csvParser.parseServiceOrdersToLines(csvServiceOrders);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
    }

    private void validateServiceOrder(Long lodgerId, Long serviceId) {
        serviceService.findById(serviceId);
        findById(lodgerId);
    }

    @Override
    public ServiceOrder findServiceOrderById(Long id) {
        for (ServiceOrder ServiceOrder : serviceOrderDao.findAll()) {
            if (ServiceOrder.getId().equals(id)) {
                return ServiceOrder;
            }
        }
        throw new ServiceException("There is not service order with this id " + id);
    }

    @Override
    public List<Service> findServiceOrderByLodgerId(Long lodgerId) {
        findById(lodgerId);
        List<ServiceOrder> serviceOrders = serviceOrderDao.findAll().stream()
                .filter(s -> lodgerId.equals(s.getLodgerId())).collect(Collectors.toList());

        List<Service> result = new LinkedList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(serviceService.findById(serviceOrder.getServiceId()));
        }
        return result;
    }

    @Override
    public Lodger findById(Long id) {
        for (Lodger lodger : lodgerDao.findAll()) {
            if (lodger.getId().equals(id)) {
                return lodger;
            }
        }
        throw new ServiceException("There is not lodger with this id " + id);
    }
}
