package com.senla.hotel.formatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.domain.Room;
import com.senla.hotel.domain.Service;

public class HotelFormatterImpl implements HotelFormatter {

    public static final String SPASE = " ";
    public static final String NEXT_LINE = "\n";

    @Override
    public String formatRooms(List<Room> rooms) {
        StringBuilder result = new StringBuilder();

        result.append("Rooms:").append(NEXT_LINE);
        for (Room room : rooms) {
            result.append(room.getId()).append(SPASE).append(room.getNumber()).append(SPASE).append(room.getCost())
                    .append(SPASE).append(room.getCapacity()).append(SPASE).append(room.getStars()).append(SPASE)
                    .append(room.isSettled()).append(SPASE).append(room.isRepaired()).append(NEXT_LINE);
        }
        return result.toString();
    }

    @Override
    public String formatServices(List<Service> services) {
        StringBuilder result = new StringBuilder();

        result.append("Services:").append(NEXT_LINE);
        for (Service service : services) {
            result.append(service.getId()).append(SPASE).append(service.getName()).append(SPASE)
                    .append(service.getCost()).append(NEXT_LINE);
        }
        return result.toString();
    }

    @Override
    public String formatLodgers(List<Lodger> lodgers) {
        StringBuilder result = new StringBuilder();

        result.append("Lodgers:").append(NEXT_LINE);
        for (Lodger lodger : lodgers) {
            result.append(lodger.getId()).append(SPASE).append(lodger.getFirstLastName()).append(SPASE)
            .append(lodger.getPhoneNumber()).append(SPASE).append(NEXT_LINE);
        }
        return result.toString();
    }

    @Override
    public String formatLodgerReversations(List<Reservation> reservations) {
        StringBuilder result = new StringBuilder();

        result.append("Reservations:").append(NEXT_LINE);
        for (Reservation reservation : reservations) {
            result.append(reservation.getId()).append(SPASE).append(reservation.getLodgerId()).append(SPASE)
                    .append(reservation.getRoomId()).append(SPASE).append(reservation.getStartDate()).append(SPASE)
                    .append(reservation.getEndDate()).append(SPASE).append(NEXT_LINE);
        }
        return result.toString();
    }
    
    @Override
    public String formatLodgerReversationsCost(Map<Lodger, BigDecimal> reservations) {
        StringBuilder result = new StringBuilder();
        
        result.append("Costs:").append(NEXT_LINE);
        for (Map.Entry<Lodger, BigDecimal> reservation : reservations.entrySet()) {
            result.append(reservation.getKey().getFirstLastName()).append(" : ").append(reservation.getValue()).append(NEXT_LINE);
        }
        return result.toString();
    }
}
