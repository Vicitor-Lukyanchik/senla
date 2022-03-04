package com.senla.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;

public interface LodgerService {

    void create(String firstName, String lastName, String phone);

    void importLodgers();

    void exportLodger(Long id);

    List<Lodger> findAll();

    void createReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId, Boolean reserved);

    void importReservations();

    void exportReservation(Long id);

    void updateReservationReserved(Long lodgerId, Long roomId);

    Map<Integer, BigDecimal> findReservationCostByLodgerId(Long lodgerId);

    Map<LocalDate, Lodger> findLastReservationsByRoomId(Long roomId, int limit);

    Map<Lodger, Room> findAllNowLodgersRooms();

    List<Room> findAllNotSettledRoomOnDate(LocalDate date);

    Reservation findReservationById(Long id);

    void createServiceOrder(LocalDate startDate, Long lodgerId, Long serviceId);

    void importServiceOrders();

    void exportServiceOrder(Long id);

    Lodger findById(Long id);

    ServiceOrder findServiceOrderById(Long id);

    List<Service> findServiceOrderByLodgerId(Long lodgerId);
}
