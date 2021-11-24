package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
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
        roomService.updateSettle(reservation.getRoomId());
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
    }

    @Override
    public void updateReservationEndDate(Integer id, LocalDate date) {
        Reservation reservation = findReservation(id);
        reservation.setEndDate(date);
    }

    private Reservation findReservation(Integer id) {
        for (Reservation reservation : hotel.getReservations()) {
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        throw new IllegalArgumentException("There is not reservation with this id");
    }

    @Override
    public Map<Lodger, Room> findAllLodgersRooms() {
        Map<Lodger, Room> result = new LinkedHashMap<>();
        List<Reservation> reservations = hotel.getReservations();
        for (Reservation reservation : reservations) {
            Room room = roomService.find(reservation.getRoomId());
            if(room.isSettled()) {
                Lodger lodger = find(reservation.getLodgerId());
                result.put(lodger, room);
            }
        }
        return result;
    }

    @Override
    public Map<Lodger, BigDecimal> findReservationCostByLodgerId(Integer lodgerId) {
        List<Reservation> reservations = hotel.getReservations().stream().filter(r -> lodgerId.equals(r.getLodgerId()))
                .collect(Collectors.toList());
        Map<Lodger, BigDecimal> result = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            Room room = roomService.find(reservation.getRoomId());
            Lodger lodger = find(reservation.getLodgerId());
            Period period = Period.between(reservation.getStartDate(), reservation.getEndDate());
            BigDecimal cost = new BigDecimal(room.getCost().intValue() * period.getDays());
            result.put(lodger, cost);
        }
        return result;
    }

    @Override
    public Map<Lodger, LocalDate> findReservationsByRoomId(Integer roomId) {
        List<Reservation> reservations = hotel.getReservations().stream().filter(r -> roomId.equals(r.getRoomId()))
                .collect(Collectors.toList());
        Map<Lodger, LocalDate> result = new LinkedHashMap<>();

        for (Reservation reservation : reservations) {
            Lodger lodger = find(reservation.getLodgerId());
            result.put(lodger, reservation.getStartDate());
        }
        return result;
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
        for(ServiceOrder serviceOrder : serviceOrders) {
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
