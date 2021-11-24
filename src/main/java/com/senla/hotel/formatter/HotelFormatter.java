package com.senla.hotel.formatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

public interface HotelFormatter {
    String formatRooms(List<Room> rooms);
    
    String formatServices(List<Service> services);
    
    String formatLodgers(List<Lodger> lodgers);
    
    String formatLodgerReversations(List<Reservation> reservations);
    
    String formatLodgerReversationsCost(Map<Lodger, BigDecimal> reservations);
    
    String formatLodgersRooms(Map<Lodger, Room> lodgersRooms);
}
