package com.senla.hotel.modul.formatter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

public interface HotelFormatter {

    String formatRooms(List<Room> rooms);
    
    String formatRoomsCosts(List<Room> rooms);

    String formatServices(List<Service> services);
    
    String formatServicesCosts(List<Service> services);

    String formatLodgers(List<Lodger> lodgers);

    String formatLodgerReversationsCost(Map<Integer, BigDecimal> reservations);

    String formatLodgersRooms(Map<Lodger, Room> lodgersRooms);
    
    String formatLastRoomReservations(Map<LocalDate, Lodger> reservations);

    String formatLodgerServices(List<Service> services);
}
