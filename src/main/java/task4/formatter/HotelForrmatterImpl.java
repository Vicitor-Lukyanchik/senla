package task4.formatter;

import task4.domain.Hotel;
import task4.domain.Room;
import task4.domain.Service;

public class HotelForrmatterImpl implements HotelForrmatter {

    public static final String SPASE = " ";
    public static final String NEXT_LINE = "\n";

    @Override
    public String formatHotel(Hotel hotel) {
        return formatRooms(hotel) + formatServices(hotel);
    }

    private String formatRooms(Hotel hotel) {
        StringBuilder result = new StringBuilder();
        result.append("Rooms:").append(NEXT_LINE);
        
        for (int i = 0; i < hotel.getRooms().size(); i++) {
            Room room = hotel.getRooms().get(i);       
            result.append(i + 1).append(SPASE).append(room.getCost()).append(SPASE).append(room.isSettled())
                    .append(SPASE).append(room.isRepaired()).append(NEXT_LINE);
        }
        return result.toString();
    }

    private String formatServices(Hotel hotel) {
        StringBuilder result = new StringBuilder();
        result.append("Services:").append(NEXT_LINE);
        
        for (int i = 0; i < hotel.getServices().size(); i++) {
            Service service = hotel.getServices().get(i);         
            result.append(i + 1).append(SPASE).append(service.getName()).append(SPASE).append(service.getCost())
                    .append(NEXT_LINE);
        }
        return result.toString();
    }
}
