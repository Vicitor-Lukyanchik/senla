package com.senla.hotel.service.impl;

import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.*;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.file.CsvFileReader;
import com.senla.hotel.file.CsvFileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.connection.hibernate.HibernateUtil;
import com.senla.hotel.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Log4j2
public class LodgerServiceImpl implements LodgerService {

    private static final int LIMIT = 3;
    private static final String LODGERS_PATH = "lodgers.csv";
    private static final String RESERVATIONS_PATH = "reservations.csv";
    private static final String SERVICE_ORDERS_PATH = "service_orders.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final LocalDate LOCAL_DATE_NOW = LocalDate.parse("07.02.2022", DATE_TIME_FORMATTER);

    @Autowired
    private CsvFileReader fileReader;
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private CsvFileWriter fileWriter;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private LodgerDao lodgerDao;
    @Autowired
    private ServiceOrderDao serviceOrderDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private HibernateUtil hibernateUtil;
    private List<Lodger> csvLodgers = new ArrayList<>();
    private List<Reservation> csvReservations = new ArrayList<>();
    private List<ServiceOrder> csvServiceOrders = new ArrayList<>();

    @Override
    public void create(Lodger lodger) {
        validateLodger(lodger.getFirstName(), lodger.getLastName(), lodger.getPhoneNumber());
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        lodgerDao.setType(Lodger.class);
        try {
            lodgerDao.create(lodger, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void importLodgers() {
        csvLodgers = getLodgersFromFile();
        for (Lodger importLodger : csvLodgers) {
            validateLodger(importLodger.getFirstName(), importLodger.getLastName(), importLodger.getPhoneNumber());
            try {
                findById(importLodger.getId());
                update(importLodger);
            } catch (ServiceException e) {
                createWithId(importLodger);
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

    private void update(Lodger lodger) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        lodgerDao.setType(Lodger.class);
        try {
            lodgerDao.update(lodger, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    private void createWithId(Lodger importLodger) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            lodgerDao.create(importLodger, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
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
    public void createReservation(Reservation reservation) {
        Lodger lodger = new Lodger();
        Room room = new Room();
        lodger.setId(reservation.getLodger().getId());
        room.setId(reservation.getRoom().getId());
        validateReservation(reservation.getStartDate(), reservation.getEndDate(), lodger, room);

        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        reservationDao.setType(Reservation.class);
        try {
            reservationDao.create(reservation, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void importReservations() {
        csvReservations = getReservationsFromFile();
        for (Reservation importReservation : csvReservations) {
            validateReservation(importReservation.getStartDate(), importReservation.getEndDate(),
                    importReservation.getLodger(), importReservation.getRoom());
            try {
                findReservationById(importReservation.getId());
                updateReservation(importReservation);
            } catch (ServiceException e) {
                createReservationWithId(importReservation);
            }
        }
    }

    private List<Reservation> getReservationsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(RESERVATIONS_PATH);
        return csvParser.parseReservations(lines);
    }

    private void validateReservation(LocalDate startDate, LocalDate endDate, Lodger lodger, Room room) {
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

    private void createReservationWithId(Reservation importReservation) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        reservationDao.setType(Reservation.class);
        try {
            reservationDao.create(importReservation, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
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

    private void updateReservation(Reservation reservation) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        reservationDao.setType(Reservation.class);
        try {
            reservationDao.update(reservation, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
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
    public Reservation findReservationById(Long id) {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        reservationDao.setType(Reservation.class);
        Reservation reservation = reservationDao.findById(session, id);
        if (reservation == null) {
            throw new ServiceException("There is not reservation with this id " + id);
        }
        return reservation;
    }

    private List<Reservation> findAllReservations() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        reservationDao.setType(Reservation.class);
        List<Reservation> result = reservationDao.findAll(session);
        session.close();
        return result;
    }

    @Override
    public void createServiceOrder(ServiceOrder serviceOrder) {
        Lodger lodger = new Lodger();
        Service service = new Service();
        lodger.setId(serviceOrder.getLodger().getId());
        service.setId(serviceOrder.getService().getId());
        validateServiceOrder(lodger, service);
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            serviceOrderDao.setType(ServiceOrder.class);
            serviceOrderDao.create(serviceOrder, session);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void importServiceOrders() {
        csvServiceOrders = getServiceOrdersFromFile();
        for (ServiceOrder importServiceOrder : csvServiceOrders) {
            validateServiceOrder(importServiceOrder.getLodger(), importServiceOrder.getService());
            try {
                findServiceOrderById(importServiceOrder.getId());
                updateServiceOrder(importServiceOrder);
            } catch (ServiceException ex) {
                createServiceOrderWithId(importServiceOrder);
            }
        }
    }

    private List<ServiceOrder> getServiceOrdersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(SERVICE_ORDERS_PATH);
        return csvParser.parseServiceOrders(lines);
    }

    private void validateServiceOrder(Lodger lodger, Service service) {
        serviceService.findById(service.getId());
        findById(lodger.getId());
    }

    private void createServiceOrderWithId(ServiceOrder importServiceOrder) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            serviceOrderDao.create(importServiceOrder, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    private void updateServiceOrder(ServiceOrder serviceOrder) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            serviceOrderDao.setType(ServiceOrder.class);
            serviceOrderDao.update(serviceOrder, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void exportServiceOrder(Long id) {
        ServiceOrder serviceOrder = findServiceOrderById(id);
        ServiceOrder exportServiceOrder = csvServiceOrders.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElse(null);
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
    public ServiceOrder findServiceOrderById(Long id) {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        serviceOrderDao.setType(ServiceOrder.class);
        ServiceOrder serviceOrder = serviceOrderDao.findById(session, id);
        if (serviceOrder == null) {
            throw new ServiceException("There is not service order with this id " + id);
        }
        return serviceOrder;
    }

    @Override
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

    public List<ServiceOrder> findAllServiceOrders() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        serviceOrderDao.setType(ServiceOrder.class);
        List<ServiceOrder> result = serviceOrderDao.findAll(session);
        session.close();
        return result;
    }

    @Override
    public Lodger findById(Long id) {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        lodgerDao.setType(Lodger.class);
        Lodger lodger = lodgerDao.findById(session, id);
        if (lodger == null) {
            throw new ServiceException("There is not lodger with this id " + id);
        }
        return lodger;
    }

    @Override
    public List<Lodger> findAll() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        lodgerDao.setType(Lodger.class);
        List<Lodger> result = lodgerDao.findAll(session);
        session.close();
        return result;
    }
}
