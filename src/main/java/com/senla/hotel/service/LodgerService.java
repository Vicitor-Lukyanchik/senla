package com.senla.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

public interface LodgerService {

    void create(String firstName, String lastName, String phone);

    List<Lodger> findAll();

    void createReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId);

    void updateReservationIsReserved(Long lodgerId, Long roomId);

    Map<Integer, BigDecimal> findReservationCostByLodgerId(Long lodgerId);

    Map<LocalDate, Lodger> findLastReservationsByRoomId(Long roomId, int limit);

    Map<Lodger, Room> findAllNowLodgersRooms();

    List<Room> findAllNotSettledRoomOnDate(LocalDate date);

    void createSeviceOrder(LocalDate startDate, Long lodgerId, Long serviceId);

    List<Service> findServiceOrderByLodgerId(Long lodgerId);
}
