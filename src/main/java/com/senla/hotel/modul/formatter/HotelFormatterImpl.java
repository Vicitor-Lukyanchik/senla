package com.senla.hotel.modul.formatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

public class HotelFormatterImpl implements HotelFormatter {

    public static final String SPASE = " ";
    public static final String NEXT_LINE = "\n";
    public static final String TABULATION = "\t";

    @Override
    public String formatRooms(List<Room> rooms) {
        StringBuilder result = new StringBuilder();

        result.append("\nRooms:").append(NEXT_LINE);

        if (!rooms.isEmpty()) {
            result.append("id").append(SPASE).append(TABULATION).append("number").append(SPASE).append(TABULATION)
                    .append("cost").append(SPASE).append(TABULATION).append("cap").append(SPASE).append(TABULATION)
                    .append("stars").append(SPASE).append(TABULATION).append("settle").append(SPASE).append(TABULATION)
                    .append("repaired").append(NEXT_LINE);

            for (Room room : rooms) {
                result.append(room.getId()).append(SPASE).append(TABULATION).append(room.getNumber()).append(SPASE)
                        .append(TABULATION).append(room.getCost()).append(SPASE).append(TABULATION)
                        .append(room.getCapacity()).append(SPASE).append(TABULATION).append(room.getStars())
                        .append(SPASE).append(TABULATION).append(room.isSettled()).append(SPASE).append(TABULATION)
                        .append(room.isRepaired()).append(NEXT_LINE);
            }
        }
        return result.toString();
    }

    @Override
    public String formatServices(List<Service> services) {
        StringBuilder result = new StringBuilder();

        if (!services.isEmpty()) {
            result.append("\nServices : ").append(NEXT_LINE);
            result.append("id").append(TABULATION).append("service").append(TABULATION).append("cost").append(NEXT_LINE);
            
            for (Service service : services) {
                result.append(service.getId()).append(TABULATION).append(service.getName()).append(TABULATION).append(service.getCost()).append(TABULATION)
                        .append(NEXT_LINE);
            }
        }
        return result.toString();
    }

    @Override
    public String formatLodgers(List<Lodger> lodgers) {
        StringBuilder result = new StringBuilder();

        result.append("\nLodgers:").append(NEXT_LINE);
        for (Lodger lodger : lodgers) {
            result.append(lodger.getId()).append(SPASE).append(lodger.getFirstLastName()).append(SPASE)
                    .append(lodger.getPhoneNumber()).append(SPASE).append(NEXT_LINE);
        }
        return result.toString();
    }

    @Override
    public String formatLodgerReversationsCost(Map<Lodger, BigDecimal> reservations) {
        StringBuilder result = new StringBuilder();

        result.append("\nCosts:").append(NEXT_LINE);
        for (Map.Entry<Lodger, BigDecimal> reservation : reservations.entrySet()) {
            result.append(reservation.getKey().getFirstLastName()).append(" : ").append(reservation.getValue())
                    .append(NEXT_LINE);
        }
        return result.toString();
    }

    @Override
    public String formatLodgersRooms(Map<Lodger, Room> lodgersRooms) {
        StringBuilder result = new StringBuilder();

        result.append("\nLodgers room : ").append(NEXT_LINE);

        if (!lodgersRooms.isEmpty()) {
            result.append("FI").append(TABULATION).append("room number").append(NEXT_LINE);

            for (Map.Entry<Lodger, Room> lodgersRoom : lodgersRooms.entrySet()) {
                result.append(lodgersRoom.getKey().getFirstLastName()).append(TABULATION)
                        .append(lodgersRoom.getValue().getNumber()).append(NEXT_LINE);
            }
        }
        return result.toString();
    }
    
    @Override
    public String formatLodgerServices(List<Service> services) {
        StringBuilder result = new StringBuilder();

        if (!services.isEmpty()) {
            result.append("\nLodgers services : ").append(NEXT_LINE);
            result.append("service").append(TABULATION).append("cost").append(NEXT_LINE);
            
            for (Service service : services) {
                result.append(service.getName()).append(TABULATION).append(service.getCost()).append(TABULATION)
                        .append(NEXT_LINE);
            }
        }
        return result.toString();
    }
}
