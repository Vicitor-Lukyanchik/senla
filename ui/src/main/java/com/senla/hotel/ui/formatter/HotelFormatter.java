package com.senla.hotel.ui.formatter;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HotelFormatter {

    String formatRooms(List<Room> rooms);

    String formatRoomsCosts(List<Room> rooms);

    String formatServices(List<Service> services);

    String formatServicesCosts(List<Service> services);

    String formatLodgerReservationsCost(Map<Integer, BigDecimal> reservations);

    String formatLodgersRooms(Map<Lodger, Room> lodgersRooms);

    String formatLastRoomReservations(Map<LocalDate, Lodger> reservations);

    String formatLodgerServices(List<Service> services);
}