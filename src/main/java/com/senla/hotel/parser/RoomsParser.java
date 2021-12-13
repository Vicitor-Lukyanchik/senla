package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.Room;

public interface RoomsParser {
    List<Room> parseRooms(List<String> rooms);
    
    List<String> parseLines(List<Room> rooms);
}
