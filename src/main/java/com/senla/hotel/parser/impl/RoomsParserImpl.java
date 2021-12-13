package com.senla.hotel.parser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.parser.RoomsParser;

public class RoomsParserImpl implements RoomsParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<Room> parseRooms(List<String> rooms) {
        List<Room> result = new ArrayList<>();
        for (String room : rooms) {
            result.add(parseRoom(room.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Room parseRoom(String[] room) {
        Long id = Long.parseLong(room[0]);
        int number = Integer.parseInt(room[1]);
        BigDecimal cost = new BigDecimal(room[2]);
        int capacity = Integer.parseInt(room[3]);
        int stars = Integer.parseInt(room[4]);
        boolean isRepaired = room[5].equals("true");
        return new Room(id, number, cost, capacity, stars, isRepaired);
    }

    @Override
    public List<String> parseLines(List<Room> rooms) {
        List<String> result = new ArrayList<>();
        for (Room room : rooms) {
           result.add(parseLine(room));
        }
        return result;
    }

    private String parseLine(Room room) {
        String isRepaired = "false";
        if (room.isRepaired()) {
            isRepaired = "true";
        }
        return room.getId() + NEXT_COLUMN + room.getNumber() + NEXT_COLUMN + room.getCost() + NEXT_COLUMN
                + room.getCapacity() + NEXT_COLUMN + room.getStars() + NEXT_COLUMN + isRepaired;
    }
}
