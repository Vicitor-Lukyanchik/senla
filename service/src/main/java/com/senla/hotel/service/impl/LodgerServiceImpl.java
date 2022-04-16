package com.senla.hotel.service.impl;

import com.senla.hotel.entity.*;
import com.senla.hotel.file.CsvFileReader;
import com.senla.hotel.file.CsvFileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Log4j2
public class LodgerServiceImpl implements LodgerService {

    private static final int LIMIT = 3;
    private static final String LODGERS_PATH = "lodgers.csv";
    private static final String RESERVATIONS_PATH = "reservations.csv";
    private static final String SERVICE_ORDERS_PATH = "service_orders.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final LocalDate LOCAL_DATE_NOW = LocalDate.parse("07.02.2022", DATE_TIME_FORMATTER);

    private final CsvFileReader fileReader;
    private final CsvParser csvParser;
    private final CsvFileWriter fileWriter;
    private final ServiceService serviceService;
    private final RoomService roomService;
    private final LodgerRepository lodgerRepository;
    private final ServiceOrderRepository serviceOrderRepository;
    private final ReservationRepository reservationRepository;

    private List<Lodger> csvLodgers = new ArrayList<>();
    private List<Reservation> csvReservations = new ArrayList<>();
    private List<ServiceOrder> csvServiceOrders = new ArrayList<>();

    @Override
    @Transactional
    public void importLodgers() {
        csvLodgers = getLodgersFromFile();
        for (Lodger importLodger : csvLodgers) {
            validateLodger(importLodger.getFirstName(), importLodger.getLastName(), importLodger.getPhoneNumber());
            try {
                findById(importLodger.getId());
                update(importLodger);
            } catch (ServiceException e) {
                create(importLodger);
            }
        }
    }

    private List<Lodger> getLodgersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(LODGERS_PATH);
        return csvParser.parseLodgers(lines);
    }

    private void validateLodger(String firstName, String lastName, String phone) {
        if (firstName.length() > 25 || lastName.length() > 25) {
            String message = "First-last name length can not be more than 25";
            log.error(message);
            throw new ServiceException(message);
        }
        if (firstName.length() < 2 || lastName.length() < 2) {
            String message = "First-last name length can not be less than 2";
            log.error(message);
            throw new ServiceException(message);
        }
        if (!Character.isUpperCase(firstName.charAt(0)) || !Character.isUpperCase(lastName.charAt(0))) {
            String message = "First letter should be uppercase";
            log.error(message);
            throw new ServiceException(message);
        }
        if (phone.length() != 7) {
            String message = "Phone number length should be 7";
            log.warn(message);
            throw new ServiceException(message);
        }
    }

    @Transactional
    public void update(Lodger lodger) {
        lodgerRepository.update(lodger);
    }

    @Override
    @Transactional
    public void create(Lodger lodger) {
        validateLodger(lodger.getFirstName(), lodger.getLastName(), lodger.getPhoneNumber());
        lodgerRepository.create(lodger);
    }

    @Override
    @Transactional
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
    @Transactional
    public void importReservations() {
        csvReservations = getReservationsFromFile();
        for (Reservation importReservation : csvReservations) {
            validateReservation(importReservation.getStartDate(), importReservation.getEndDate(),
                    importReservation.getLodger(), importReservation.getRoom());
            try {
                findReservationById(importReservation.getId());
                updateReservation(importReservation);
            } catch (ServiceException e) {
                createReservation(importReservation);
            }
        }
    }

    @Override
    @Transactional
    public void createReservation(Reservation reservation) {
        Lodger lodger = new Lodger();
        Room room = new Room();
        lodger.setId(reservation.getLodger().getId());
        room.setId(reservation.getRoom().getId());
        validateReservation(reservation.getStartDate(), reservation.getEndDate(), lodger, room);

        reservationRepository.create(reservation);
    }

    private List<Reservation> getReservationsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(RESERVATIONS_PATH);
        return csvParser.parseReservations(lines);
    }

    @Transactional
    public void validateReservation(LocalDate startDate, LocalDate endDate, Lodger lodger, Room room) {
        findById(lodger.getId());
        if (startDate.isAfter(endDate)) {
            String message = "Start date can not be after than end date";
            log.error(message);
            throw new ServiceException(message);
        }

        roomService.findById(room.getId());
        List<Reservation> roomReservations = findAllReservations().stream()
                .filter(r -> room.getId().equals(r.getRoom().getId())).collect(Collectors.toList());
        if (isRoomSettledOnDates(roomReservations, startDate, endDate)) {
            String message = "Room is settled on this dates";
            log.error(message);
            throw new ServiceException(message);
        }
    }

    @Override
    @Transactional
    public void exportReservation(Long id) {
        Reservation reservation = findReservationById(id);
        Reservation exportReservation = csvReservations.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElse(null);
        if (exportReservation == null) {
            csvReservations.add(reservation);
        } else {
            exportReservation.setLodger(reservation.getLodger());
            exportReservation.setRoom(reservation.getRoom());
            exportReservation.setStartDate(reservation.getStartDate());
            exportReservation.setEndDate(reservation.getEndDate());
        }
        List<String> lines = csvParser.parseLodgersToLines(csvLodgers);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
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
    @Transactional
    public void updateReservationReserved(Long lodgerId, Long roomId) {
        Reservation reservation = findAllReservations().stream().filter(
                        lodgerRoom -> roomId.equals(lodgerRoom.getRoom().getId()) && lodgerId.equals(lodgerRoom.getLodger().getId()))
                .findFirst().orElseThrow(() -> new ServiceException("There is not room or lodger with this id"));

        if (reservation.isReserved()) {
            reservation.setReserved(false);
            updateReservation(reservation);
        } else {
            throw new ServiceException("This reservation is closed");
        }
    }

    @Transactional
    public void updateReservation(Reservation reservation) {
        reservationRepository.update(reservation);
    }

    @Override
    @Transactional
    public Map<Lodger, Room> findAllLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = findAllReservations();

        for (Reservation reservation : reservations) {
            Lodger lodger = findById(reservation.getLodger().getId());
            Room room = roomService.findById(reservation.getRoom().getId());
            result.put(lodger, room);

        }
        return result;
    }

    @Override
    @Transactional
    public Map<LocalDate, Lodger> findLastReservationsByRoomId(Long roomId) {
        Map<LocalDate, Lodger> result = new LinkedHashMap<>();
        List<Reservation> reservations = findAllReservations().stream()
                .filter(r -> roomId.equals(r.getRoom().getId()))
                .sorted(Comparator.comparing(Reservation::getStartDate).reversed()).limit(LIMIT)
                .collect(Collectors.toList());

        for (Reservation reservation : reservations) {
            result.put(reservation.getStartDate(), findById(reservation.getLodger().getId()));
        }
        return result;
    }

    @Override
    @Transactional
    public Map<Long, BigDecimal> findReservationCostByLodgerId(Long lodgerId) {
        findById(lodgerId);
        List<Reservation> reservations = findAllReservations().stream()
                .filter(r -> lodgerId.equals(r.getLodger().getId()) && r.isReserved()).collect(Collectors.toList());
        Map<Long, BigDecimal> result = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            Room room = roomService.findById(reservation.getRoom().getId());
            Period period = Period.between(reservation.getStartDate(), reservation.getEndDate());
            BigDecimal cost = new BigDecimal(room.getCost().intValue() * period.getDays());
            result.put(room.getId(), cost);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Room> findAllNotSettledRoomOnDate(String data) {
        List<Room> result = new LinkedList<>();
        for (Room room : roomService.findAll()) {
            List<Reservation> roomReservations = findAllReservations().stream().filter(r -> room.getId().equals(r.getRoom().getId()))
                    .collect(Collectors.toList());
            if (isRoomNotSettledOnDate(roomReservations, LocalDate.parse(data, DATE_TIME_FORMATTER))) {
                result.add(room);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Integer findAllNotSettledRooms() {
        List<Room> result = new LinkedList<>();
        for (Room room : roomService.findAll()) {
            List<Reservation> roomReservations = findAllReservations().stream().filter(r -> room.getId().equals(r.getRoom().getId()))
                    .collect(Collectors.toList());
            if (isRoomNotSettledOnDate(roomReservations, LOCAL_DATE_NOW)) {
                result.add(room);
            }
        }
        return result.size();
    }

    private boolean isRoomNotSettledOnDate(List<Reservation> reservations, LocalDate date) {
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                if (reservation.getStartDate().isBefore(date) && reservation.getEndDate().isAfter(date) ||
                        reservation.getStartDate().equals(date) ||
                        reservation.getEndDate().equals(date)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Reservation findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(Reservation.class, id);
        if (reservation == null) {
            throw new ServiceException("There is not reservation with this id " + id);
        }
        return reservation;
    }

    @Transactional
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll(Reservation.class);
    }

    @Override
    @Transactional
    public void importServiceOrders() {
        csvServiceOrders = getServiceOrdersFromFile();
        for (ServiceOrder importServiceOrder : csvServiceOrders) {
            validateServiceOrder(importServiceOrder.getLodger(), importServiceOrder.getService());
            try {
                findServiceOrderById(importServiceOrder.getId());
                updateServiceOrder(importServiceOrder);
            } catch (ServiceException ex) {
                createServiceOrder(importServiceOrder);
            }
        }
    }

    @Override
    @Transactional
    public void createServiceOrder(ServiceOrder serviceOrder) {
        Lodger lodger = new Lodger();
        Service service = new Service();
        lodger.setId(serviceOrder.getLodger().getId());
        service.setId(serviceOrder.getService().getId());
        validateServiceOrder(lodger, service);

        serviceOrderRepository.create(serviceOrder);
    }

    private List<ServiceOrder> getServiceOrdersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(SERVICE_ORDERS_PATH);
        return csvParser.parseServiceOrders(lines);
    }

    private void validateServiceOrder(Lodger lodger, Service service) {
        serviceService.findById(service.getId());
        findById(lodger.getId());
    }

    @Transactional
    public void updateServiceOrder(ServiceOrder serviceOrder) {
        serviceOrderRepository.update(serviceOrder);
    }

    @Override
    @Transactional
    public void exportServiceOrder(Long id) {
        ServiceOrder serviceOrder = findServiceOrderById(id);
        ServiceOrder exportServiceOrder = csvServiceOrders.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (exportServiceOrder == null) {
            csvServiceOrders.add(serviceOrder);
        } else {
            exportServiceOrder.setLodger(serviceOrder.getLodger());
            exportServiceOrder.setService(serviceOrder.getService());
            exportServiceOrder.setDate(serviceOrder.getDate());
        }
        List<String> lines = csvParser.parseServiceOrdersToLines(csvServiceOrders);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
    }

    @Override
    @Transactional
    public ServiceOrder findServiceOrderById(Long id) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(ServiceOrder.class, id);
        if (serviceOrder == null) {
            throw new ServiceException("There is not service order with this id " + id);
        }
        return serviceOrder;
    }

    @Override
    @Transactional
    public List<Service> findServiceOrderByLodgerId(Long lodgerId) {
        findById(lodgerId);
        List<ServiceOrder> serviceOrders = findAllServiceOrders().stream()
                .filter(s -> lodgerId.equals(s.getLodger().getId())).collect(Collectors.toList());

        List<Service> result = new LinkedList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(serviceService.findById(serviceOrder.getService().getId()));
        }
        return result;
    }

    @Transactional
    public List<ServiceOrder> findAllServiceOrders() {
        return serviceOrderRepository.findAll(ServiceOrder.class);
    }

    @Override
    @Transactional
    public Lodger findById(Long id) {
        Lodger lodger = lodgerRepository.findById(Lodger.class, id);
        if (lodger == null) {
            throw new ServiceException("There is not lodger with this id " + id);
        }
        return lodger;
    }

    @Override
    @Transactional
    public List<Lodger> findAll() {
        return lodgerRepository.findAll(Lodger.class);
    }
}
