package com.senla.hotel.service;

import com.senla.hotel.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LodgerService {

    void create(Lodger lodger);

    void importLodgers();

    void exportLodger(Long id);

    List<Lodger> findAll();

    void createReservation(Reservation reservation);

    void importReservations();

    void exportReservation(Long id);

    void updateReservationReserved(Long lodgerId, Long roomId);

    Map<Long, BigDecimal> findReservationCostByLodgerId(Long lodgerId);

    Map<LocalDate, Lodger> findLastReservationsByRoomId(Long roomId);

    Map<Lodger, Room> findAllLodgersRooms();

    Integer findAllNotSettledRooms();

    List<Room> findAllNotSettledRoomOnDate(String data);

    Reservation findReservationById(Long id);

    void createServiceOrder(ServiceOrder serviceOrder);

    void importServiceOrders();

    void exportServiceOrder(Long id);

    Lodger findById(Long id);

    ServiceOrder findServiceOrderById(Long id);

    List<Service> findServiceOrderByLodgerId(Long lodgerId);
}
