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
        int index = 1;

        result.append("Rooms:").append(NEXT_LINE);
        for (Room room : hotel.getRooms()) {
            result.append(index++).append(SPASE).append(room.getCost()).append(SPASE).append(room.isSettled())
                    .append(SPASE).append(room.isRepaired()).append(NEXT_LINE);
        }
        return result.toString();
    }

    private String formatServices(Hotel hotel) {
        StringBuilder result = new StringBuilder();
        int index = 1;

        result.append("Services:").append(NEXT_LINE);
        for (Service service : hotel.getServices()) {
            result.append(index++).append(SPASE).append(service.getName()).append(SPASE).append(service.getCost())
                    .append(NEXT_LINE);
        }
        return result.toString();
    }
}
