package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;
import com.senla.hotel.domain.ServiceOrder;

public interface CsvParser {
    List<Room> parseRooms(List<String> rooms);
    
    List<String> parseRoomsToLines(List<Room> rooms);
 
    List<Lodger> parseLodgers(List<String> lodgers);
    
    List<String> parseLodgersToLines(List<Lodger> lodgers);
    
    List<Reservation> parseReservations(List<String> reservations);
    
    List<String> parseReservationsToLines(List<Reservation> reservations);
    
    List<ServiceOrder> parseServiceOrders(List<String> serviceOrders);
    
    List<String> parseServiceOrdersToLines(List<ServiceOrder> serviceOrders);
    
    List<Service> parseServices(List<String> services);
    
    List<String> parseServicesToLines(List<Service> services);
}
