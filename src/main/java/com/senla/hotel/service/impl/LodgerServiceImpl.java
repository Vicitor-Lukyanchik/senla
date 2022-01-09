package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;

@Singleton
public class LodgerServiceImpl implements LodgerService {

    private static final String PATH = "lodgers.csv";

    private final FileReader fileReader = ApplicationContext.getInstance().getObject(FileReader.class);
    private final CsvParser csvParser = ApplicationContext.getInstance().getObject(CsvParser.class);
    private final FileWriter fileWriter = ApplicationContext.getInstance().getObject(FileWriter.class);

    private ServiceService serviceService = ApplicationContext.getInstance().getObject(ServiceService.class);
    private RoomService roomService = ApplicationContext.getInstance().getObject(RoomService.class);
    private LodgerRepository lodgerRepository = ApplicationContext.getInstance().getObject(LodgerRepository.class);
    private ServiceOrderRepository serviceOrderRepository = ApplicationContext.getInstance()
            .getObject(ServiceOrderRepository.class);
    private ReservationRepository reservationRepository = ApplicationContext.getInstance()
            .getObject(ReservationRepository.class);
    private Long id = 0l;
    private Long reservationId = 0l;
    private Long serviceOrderId = 0l;
    private List<Lodger> importLodgers = new ArrayList<>();
    private List<Reservation> importReservations = new ArrayList<>();
    private List<ServiceOrder> importServiceOrders = new ArrayList<>();

    @Override
    public void create(String firstName, String lastName, String phone) {
        validateLodger(firstName, lastName, phone);
        lodgerRepository.addLodger(new Lodger(generateId(), firstName, lastName, phone));
        id++;
    }

    private Long generateId() {
        try {
            while (true) {
                id++;
                findById(id);
            }
        } catch (ServiceException ex) {
            return id;
        }
    }

    @Override
    public void importLodgers() {
        importLodgers = getLodgersFromFile();
        for (Lodger importLodger : importLodgers) {
            validateLodger(importLodger.getFirstName(), importLodger.getLastName(), importLodger.getPhoneNumber());
            try {
                Lodger lodger = findById(importLodger.getId());
                lodger.setFirstName(importLodger.getFirstName());
                lodger.setLastName(importLodger.getLastName());
                lodger.setPhoneNumber(importLodger.getPhoneNumber());
            } catch (ServiceException ex) {
                lodgerRepository.addLodger(new Lodger(importLodger.getId(), importLodger.getFirstName(),
                        importLodger.getLastName(), importLodger.getPhoneNumber()));
            }
        }
    }

    private List<Lodger> getLodgersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return csvParser.parseLodgers(lines);
    }

    @Override
    public void exportLodger(Long id) {
        Lodger lodger = findById(id);
        Lodger importLodger = importLodgers.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        if (importLodger == null) {
            importLodgers.add(lodger);
        } else {
            importLodger.setFirstName(lodger.getFirstName());
            importLodger.setLastName(lodger.getLastName());
            importLodger.setPhoneNumber(lodger.getPhoneNumber());
        }
        List<String> lines = csvParser.parseLodgersToLines(importLodgers);
        fileWriter.writeResourceFileLines(PATH, lines);
    }

    private void validateLodger(String firstName, String lastName, String phone) {
        if (firstName.length() > 25 || lastName.length() > 25) {
            throw new ServiceException("First-last name length can not be more than 25");
        }
        if (phone.length() != 7) {
            throw new ServiceException("Phone number length should be 7");
        }
    }

    @Override
    public List<Lodger> findAll() {
        return lodgerRepository.getLodgers();
    }

    @Override
    public void createReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        validateReservation(startDate, endDate, lodgerId, roomId);
        reservationRepository
                .addReservation(new Reservation(generateReservationId(), startDate, endDate, lodgerId, roomId));
    }

    private Long generateReservationId() {
        try {
            while (true) {
                reservationId++;
                findReservationById(reservationId);
            }
        } catch (ServiceException ex) {
            return reservationId;
        }
    }

    @Override
    public void importReservations() {
        importReservations = getReservationsFromFile();
        for (Reservation importReservation : importReservations) {
            validateReservation(importReservation.getStartDate(), importReservation.getEndDate(),
                    importReservation.getLodgerId(), importReservation.getRoomId());
            try {
                Reservation reservation = findReservationById(importReservation.getId());
                reservation.setStartDate(importReservation.getStartDate());
                reservation.setEndDate(importReservation.getEndDate());
                reservation.setLodgerId(importReservation.getLodgerId());
                reservation.setRoomId(importReservation.getRoomId());
            } catch (ServiceException ex) {
                reservationRepository.addReservation(new Reservation(importReservation.getId(),
                        importReservation.getStartDate(), importReservation.getEndDate(),
                        importReservation.getLodgerId(), importReservation.getRoomId()));
            }
        }
    }

    private List<Reservation> getReservationsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return csvParser.parseReservations(lines);
    }

    @Override
    public void exportReservation(Long id) {
        Reservation reservation = findReservationById(id);
        Reservation importReservation = importReservations.stream().filter(r -> r.getId().equals(id)).findFirst()
                .orElse(null);
        if (importReservation == null) {
            importReservations.add(reservation);
        } else {
            importReservation.setLodgerId(reservation.getLodgerId());
            importReservation.setRoomId(reservation.getRoomId());
            importReservation.setStartDate(reservation.getStartDate());
            importReservation.setEndDate(reservation.getEndDate());
        }
        List<String> lines = csvParser.parseLodgersToLines(importLodgers);
        fileWriter.writeResourceFileLines(PATH, lines);
    }

    private void validateReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        findById(lodgerId);
        if (startDate.isAfter(endDate)) {
            throw new ServiceException("Start date can not be after than end date");
        }

        Room room = roomService.findById(roomId);
        List<Reservation> roomReservations = reservationRepository.getReservations().stream()
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
    public void updateReservationIsReserved(Long lodgerId, Long roomId) {
        Reservation reservation = reservationRepository.getReservations().stream().filter(
                lodgerRoom -> roomId.equals(lodgerRoom.getRoomId()) && lodgerId.equals(lodgerRoom.getLodgerId()))
                .findFirst().orElseThrow(() -> new ServiceException("There is not room with this id"));

        if (reservation.isReserved()) {
            reservation.isNotReserved();
        } else {
            throw new ServiceException("This reservation is closed");
        }
    }

    @Override
    public Map<Lodger, Room> findAllNowLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = reservationRepository.getReservations();

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
        List<Reservation> reservations = reservationRepository.getReservations().stream()
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
        List<Reservation> reservations = reservationRepository.getReservations().stream()
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
        List<Reservation> reservations = reservationRepository.getReservations();
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
        for (Reservation reservation : reservationRepository.getReservations()) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        throw new ServiceException("There is not reservation with this id");
    }

    @Override
    public void createServiceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        validateServiceOrder(lodgerId, serviceId);
        serviceOrderRepository.addServiceOrder(new ServiceOrder(generateServiceOrderId(), date, lodgerId, serviceId));
    }

    @Override
    public void importServiceOrders() {
        importServiceOrders = getServiceOrdersFromFile();
        for (ServiceOrder importServiceOrder : importServiceOrders) {
            try {
                validateServiceOrder(importServiceOrder.getLodgerId(), importServiceOrder.getServiceId());
                ServiceOrder serviceOrder = findServiceOrderById(importServiceOrder.getId());
                serviceOrder.setDate(importServiceOrder.getDate());
                serviceOrder.setLodgerId(importServiceOrder.getLodgerId());
                serviceOrder.setServiceId(importServiceOrder.getServiceId());
            } catch (ServiceException ex) {
                validateServiceOrder(importServiceOrder.getLodgerId(), importServiceOrder.getServiceId());
                serviceOrderRepository
                        .addServiceOrder(new ServiceOrder(importServiceOrder.getId(), importServiceOrder.getDate(),
                                importServiceOrder.getLodgerId(), importServiceOrder.getServiceId()));
            }
        }
    }

    private List<ServiceOrder> getServiceOrdersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return csvParser.parseServiceOrders(lines);
    }

    @Override
    public void exportServiceOrder(Long id) {
        ServiceOrder serviceOrder = findServiceOrderById(id);
        ServiceOrder importServiceOrder = importServiceOrders.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElse(null);
        if (importServiceOrder == null) {
            importServiceOrders.add(serviceOrder);
        } else {
            importServiceOrder.setLodgerId(serviceOrder.getLodgerId());
            importServiceOrder.setServiceId(serviceOrder.getServiceId());
            importServiceOrder.setDate(serviceOrder.getDate());
        }
        List<String> lines = csvParser.parseServiceOrdersToLines(importServiceOrders);
        fileWriter.writeResourceFileLines(PATH, lines);
    }

    private Long generateServiceOrderId() {
        try {
            while (true) {
                serviceOrderId++;
                findServiceOrderById(serviceOrderId);
            }
        } catch (ServiceException ex) {
            return serviceOrderId;
        }
    }

    private void validateServiceOrder(Long lodgerId, Long serviceId) {
        serviceService.findById(serviceId);
        findById(lodgerId);
    }

    @Override
    public Lodger findById(Long id) {
        for (Lodger lodger : lodgerRepository.getLodgers()) {
            if (lodger.getId().equals(id)) {
                return lodger;
            }
        }
        throw new ServiceException("There is not lodger with this id");
    }

    @Override
    public ServiceOrder findServiceOrderById(Long id) {
        for (ServiceOrder ServiceOrder : serviceOrderRepository.getServiceOrders()) {
            if (ServiceOrder.getId().equals(id)) {
                return ServiceOrder;
            }
        }
        throw new ServiceException("There is not service order with this id");
    }

    @Override
    public List<Service> findServiceOrderByLodgerId(Long lodgerId) {
        List<ServiceOrder> serviceOrders = serviceOrderRepository.getServiceOrders().stream()
                .filter(s -> lodgerId.equals(s.getLodgerId())).collect(Collectors.toList());

        List<Service> result = new LinkedList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(serviceService.findById(serviceOrder.getServiceId()));
        }
        return result;
    }
}
