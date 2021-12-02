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

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.ServiceService;

public class LodgerServiceImpl implements LodgerService {

    private Hotel hotel;
    private ServiceService serviceService;
    private RoomService roomService;
    private Integer id = 1;
    private Integer reservationId = 1;
    private Integer serviceOrderId = 1;

    public LodgerServiceImpl(Hotel hotel) {
        this.hotel = hotel;
        serviceService = new ServiceServiceImpl(hotel);
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void create(Lodger lodger) {
        validateLodger(lodger);
        lodger.setId(id);
        id++;
        hotel.addLodger(lodger);
    }

    private void validateLodger(Lodger lodger) {
        if (lodger == null) {
            throw new IllegalArgumentException("Lodger can not be null");
        }
        if (lodger.getFirstName().length() > 25 || lodger.getLastName().length() > 25) {
            throw new IllegalArgumentException("First-last name length can not be more than 25");
        }
        if (lodger.getPhoneNumber().length() != 7) {
            throw new IllegalArgumentException("Phone number length should be 7");
        }
    }

    @Override
    public List<Lodger> findAll() {
        return hotel.getLodgers();
    }

    @Override
    public void createReservation(Reservation reservation) {
        validateReservation(reservation);
        reservation.setId(reservationId);
        reservationId++;
        hotel.addReservation(reservation);
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }
        
        find(reservation.getLodgerId());
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            throw new IllegalArgumentException("Start date can not be after than end date");
        }
        
        Room room = roomService.find(reservation.getRoomId());
        List<Reservation> roomReservations = hotel.getReservations().stream().filter(r -> room.getId().equals(r.getRoomId()))
                .collect(Collectors.toList());
        if (isRoomSettledOnDates(roomReservations, reservation.getStartDate(), reservation.getEndDate())) {
            throw new IllegalArgumentException("Room is settled on this dates");
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
    public void updateReservationIsReserved(Integer lodgerId, Integer roomId) {
        Reservation reservation = hotel.getReservations().stream().filter(
                lodgerRoom -> roomId.equals(lodgerRoom.getRoomId()) && lodgerId.equals(lodgerRoom.getLodgerId()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("There is not room with this id"));

        if (reservation.isReserved()) {
            reservation.isNotReserved();
        } else {
            throw new IllegalArgumentException("This reservation is closed");
        }
    }

    @Override
    public Map<Lodger, Room> findAllNowLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = hotel.getReservations();

        for (Reservation reservation : reservations) {
            if (reservation.isReserved()) {
                Lodger lodger = find(reservation.getLodgerId());
                Room room = roomService.find(reservation.getRoomId());
                result.put(lodger, room);
            }
        }
        return result;
    }

    @Override
    public Map<LocalDate, Lodger> findLastReservationsByRoomId(Integer roomId, int limit) {
        Map<LocalDate, Lodger> result = new LinkedHashMap<>();
        List<Reservation> reservations = hotel.getReservations().stream().filter(r -> roomId.equals(r.getRoomId()))
                .sorted(Comparator.comparing(Reservation::getStartDate)).limit(limit).collect(Collectors.toList());

        for (Reservation reservation : reservations) {
            result.put(reservation.getStartDate(), find(reservation.getLodgerId()));
        }
        return result;
    }

    @Override
    public Map<Integer, BigDecimal> findReservationCostByLodgerId(Integer lodgerId) {
        List<Reservation> reservations = hotel.getReservations().stream()
                .filter(r -> lodgerId.equals(r.getLodgerId()) && r.isReserved()).collect(Collectors.toList());
        Map<Integer, BigDecimal> result = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            Room room = roomService.find(reservation.getRoomId());
            Period period = Period.between(reservation.getStartDate(), reservation.getEndDate());
            BigDecimal cost = new BigDecimal(room.getCost().intValue() * period.getDays());
            result.put(room.getNumber(), cost);
        }
        return result;
    }

    @Override
    public List<Room> findAllNotSettledRoomOnDate(LocalDate date) {
        List<Reservation> reservations = hotel.getReservations();
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
    public void createSeviceOrder(ServiceOrder serviceOrder) {
        validateServiceOrder(serviceOrder);
        serviceOrder.setId(serviceOrderId);
        serviceOrderId++;
        hotel.addServiceOrder(serviceOrder);
    }

    private void validateServiceOrder(ServiceOrder serviceOrder) {
        if (serviceOrder == null) {
            throw new IllegalArgumentException("Service order can not be null");
        }
        serviceService.find(serviceOrder.getServiceId());
        find(serviceOrder.getLodgerId());
    }

    @Override
    public List<Service> findServiceOrderByLodgerId(Integer lodgerId) {
        List<ServiceOrder> serviceOrders = hotel.getServiceOrders().stream()
                .filter(s -> lodgerId.equals(s.getLodgerId())).collect(Collectors.toList());

        List<Service> result = new LinkedList<>();
        for (ServiceOrder serviceOrder : serviceOrders) {
            result.add(serviceService.find(serviceOrder.getServiceId()));
        }
        return result;
    }

    private Lodger find(Integer id) {
        for (Lodger lodger : hotel.getLodgers()) {
            if (lodger.getId().equals(id)) {
                return lodger;
            }
        }
        throw new IllegalArgumentException("There is not lodger with this id");
    }

    @Override
    public Hotel getHotel() {
        return hotel;
    }
}
