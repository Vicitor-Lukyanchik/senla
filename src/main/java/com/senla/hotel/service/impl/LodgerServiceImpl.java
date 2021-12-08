package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.repository.impl.LodgerRepositoryImpl;
import com.senla.hotel.repository.impl.ReservationRepositoryImpl;
import com.senla.hotel.repository.impl.ServiceOrderRepositoryImpl;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;

public class LodgerServiceImpl implements LodgerService {

    private static LodgerService instance;
    
    private ServiceService serviceService;
    private RoomService roomService;
    private LodgerRepository lodgerRepository;
    private ServiceOrderRepository serviceOrderRepository;
    private ReservationRepository reservationRepository;
    private Long id = 1l;
    private Long reservationId = 1l;
    private Long serviceOrderId = 1l;

    public LodgerServiceImpl() {
        serviceService = ServiceServiceImpl.getInstance();
        roomService = RoomServiceImpl.getInstance();
        
        lodgerRepository = LodgerRepositoryImpl.getInstance();
        serviceOrderRepository = ServiceOrderRepositoryImpl.getInstance();
        reservationRepository = ReservationRepositoryImpl.getInstance();
    }

    public static LodgerService getInstance() {
        if(instance == null) {
            instance = new LodgerServiceImpl();
        }
        return instance;
    }
    
    @Override
    public void create(String firstName, String lastName, String phone) {
        validateLodger(firstName, lastName, phone);
        lodgerRepository.addLodger(new Lodger(id, firstName, lastName, phone));
        id++;
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
        reservationRepository.addReservation(new Reservation(reservationId, startDate, endDate, lodgerId, roomId));
        reservationId++;
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
        List<Reservation> reservations = reservationRepository.getReservations().stream().filter(r -> roomId.equals(r.getRoomId()))
                .sorted(Comparator.comparing(Reservation::getStartDate)).limit(limit).collect(Collectors.toList());

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
    public void createSeviceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        validateServiceOrder(lodgerId, serviceId);
        serviceOrderRepository.addServiceOrder(new ServiceOrder(serviceId, date, lodgerId, serviceId));
        serviceOrderId++;
    }

    private void validateServiceOrder(Long lodgerId, Long serviceId) {
        serviceService.findById(serviceId);
        findById(lodgerId);
    }

    private Lodger findById(Long id) {
        for (Lodger lodger : lodgerRepository.getLodgers()) {
            if (lodger.getId().equals(id)) {
                return lodger;
            }
        }
        throw new ServiceException("There is not lodger with this id");
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
