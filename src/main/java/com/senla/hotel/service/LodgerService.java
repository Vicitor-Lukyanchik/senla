package com.senla.hotel.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;

public interface LodgerService {

    void create(Lodger lodger);
    
    List<Lodger> findAll();
    
    void createReservation(Reservation reservation);
    
    void updateReservationIsReserved(Integer lodgerId, Integer roomId);
    
    Map<Lodger, BigDecimal> findReservationCostByLodgerId(Integer lodgerId);

    Map<Lodger, LocalDate> findReservationsByRoomId(Integer roomId);

    Map<Lodger, Room> findAllNowLodgersRooms();
    
    void createSeviceOrder(ServiceOrder serviceOrder);
    
    List<Service> findServiceOrderByLodgerId(Integer lodgerId);
    
    Hotel getHotel();
}
