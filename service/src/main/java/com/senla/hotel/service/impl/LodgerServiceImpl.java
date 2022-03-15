package com.senla.hotel.service.impl;

import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.*;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
@Log4j2
public class LodgerServiceImpl implements LodgerService {

    private static final String LODGERS_PATH = "csv/lodgers.csv";
    private static final String RESERVATIONS_PATH = "csv/reservations.csv";
    private static final String SERVICE_ORDERS_PATH = "csv/service_orders.csv";

    @Autowired
    private FileReader fileReader;
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private FileWriter fileWriter;
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
    public void create(String firstName, String lastName, String phone) {
        validateLodger(firstName, lastName, phone);
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            lodgerDao.create(new Lodger(firstName, lastName, phone), session);
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
            lodgerDao.createWithId(importLodger, session);
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
    public void createReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId, Boolean reserved) {
        validateReservation(startDate, endDate, lodgerId, roomId);
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction.begin();
            reservationDao.create(new Reservation(startDate, endDate, lodgerId, roomId, reserved), session);
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
                    importReservation.getLodgerId(), importReservation.getRoomId());
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

    private void validateReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        findById(lodgerId);
        if (startDate.isAfter(endDate)) {
            String message = "Start date can not be after than end date";
            log.error(message);
            throw new ServiceException(message);
        }

        Room room = roomService.findById(roomId);
        List<Reservation> roomReservations = findAllReservations().stream()
                .filter(r -> room.getId().equals(r.getRoomId())).collect(Collectors.toList());
        if (isRoomSettledOnDates(roomReservations, startDate, endDate)) {
            String message = "Room is settled on this dates";
            log.error(message);
            throw new ServiceException(message);
        }
    }

    private void createReservationWithId(Reservation importReservation) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            reservationDao.createWithId(importReservation, session);
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
            exportReservation.setLodgerId(reservation.getLodgerId());
            exportReservation.setRoomId(reservation.getRoomId());
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
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
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
    public Map<Lodger, Room> findAllNowLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = findAllReservations();

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
        List<Reservation> reservations = findAllReservations().stream()
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
        List<Reservation> reservations = findAllReservations().stream()
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
        List<Room> result = new LinkedList<>();
        for (Room room : roomService.findAll()) {
            List<Reservation> roomReservations = findAllReservations().stream().filter(r -> room.getId().equals(r.getRoomId()))
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
        for (Reservation reservation : findAllReservations()) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        throw new ServiceException("There is not reservation with this id " + id);
    }

    private List<Reservation> findAllReservations() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        List<Reservation> result = reservationDao.findAll(session, Reservation.class);
        session.close();
        return result;
    }

    @Override
    public void createServiceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        validateServiceOrder(lodgerId, serviceId);
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            serviceOrderDao.create(new ServiceOrder(date, lodgerId, serviceId), session);
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
            validateServiceOrder(importServiceOrder.getLodgerId(), importServiceOrder.getServiceId());
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

    private void validateServiceOrder(Long lodgerId, Long serviceId) {
        serviceService.findById(serviceId);
        findById(lodgerId);
    }

    private void createServiceOrderWithId(ServiceOrder importServiceOrder) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            serviceOrderDao.createWithId(importServiceOrder, session);
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
            exportServiceOrder.setLodgerId(serviceOrder.getLodgerId());
            exportServiceOrder.setServiceId(serviceOrder.getServiceId());
            exportServiceOrder.setDate(serviceOrder.getDate());
        }
        List<String> lines = csvParser.parseServiceOrdersToLines(csvServiceOrders);
        fileWriter.writeResourceFileLines(LODGERS_PATH, lines);
    }

    @Override
    public ServiceOrder findServiceOrderById(Long id) {
        for (ServiceOrder ServiceOrder : findAllServiceOrders()) {
            if (ServiceOrder.getId().equals(id)) {
                return ServiceOrder;
            }
        }
        throw new ServiceException("There is not service order with this id " + id);
    }

    @Override
    public List<Service> findServiceOrderByLodgerId(Long lodgerId) {
        findById(lodgerId);
        List<ServiceOrder> serviceOrders = findAllServiceOrders().stream()
                .filter(s -> lodgerId.equals(s.getLodgerId())).collect(Collectors.toList());

        List<Service> result = new LinkedList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(serviceService.findById(serviceOrder.getServiceId()));
        }
        return result;
    }

    public List<ServiceOrder> findAllServiceOrders() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        List<ServiceOrder> result = serviceOrderDao.findAll(session, ServiceOrder.class);
        session.close();
        return result;
    }

    @Override
    public Lodger findById(Long id) {
        for (Lodger lodger : findAll()) {
            if (lodger.getId().equals(id)) {
                return lodger;
            }
        }
        throw new ServiceException("There is not lodger with this id " + id);
    }

    @Override
    public List<Lodger> findAll() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        List<Lodger> result = lodgerDao.findAll(session, Lodger.class);
        session.close();
        return result;
    }
}
