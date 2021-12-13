package com.senla.hotel.filerepository.impl;

import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.filerepository.RoomFileRepository;
import com.senla.hotel.parser.RoomsParser;
import com.senla.hotel.parser.impl.RoomsParserImpl;
import com.senla.hotel.reader.FileReader;
import com.senla.hotel.reader.FileReaderImpl;
import com.senla.hotel.writer.FileWriter;
import com.senla.hotel.writer.FileWriterImpl;

public class RoomFileRepositoryImpl implements RoomFileRepository {

    private static final String PATH = "rooms.csv";

    private static RoomFileRepository instance;

    private final FileReader fileReader = new FileReaderImpl();
    private final RoomsParser roomParser = new RoomsParserImpl();
    private final FileWriter fileWriter = new FileWriterImpl();

    private List<Room> rooms = getRoomsFromFile();

    private List<Room> getRoomsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return roomParser.parseRooms(lines);
    }

    public static RoomFileRepository getInstance() {
        if (instance == null) {
            instance = new RoomFileRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Room findById(Long id) {
        Room room = rooms.stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
        if (room != null) {
            return room;
        }
        throw new FileRepositoryException("There is not room with id = " + id);
    }

    @Override
    public void export(Room room) {
        try {
            Room result = findById(room.getId());
            result.setNumber(room.getNumber());
            result.setCapacity(room.getCapacity());
            result.setCost(room.getCost());
            result.setStars(room.getStars());
            result.setRepaired(room.isRepaired());
        } catch (FileRepositoryException ex) {
            rooms.add(room);
        }
        List<String> lines = roomParser.parseLines(rooms);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
}
