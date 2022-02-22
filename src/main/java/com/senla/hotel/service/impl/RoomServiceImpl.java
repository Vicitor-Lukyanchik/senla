package com.senla.hotel.service.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.RoomService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoomServiceImpl implements RoomService {

    private static final String ROOMS_PATH = "csv/rooms.csv";

    @InjectByType
    private FileReader fileReader;
    @InjectByType
    private CsvParser csvParser;
    @InjectByType
    private FileWriter fileWriter;
    @InjectByType
    private RoomDao roomDao;
    private List<Room> csvRooms = new ArrayList<>();

    @Override
    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired) {
        validateStars(stars);
        roomDao.create(new Room(number, cost, capacity, stars, isRepaired));
    }

    @Override
    public void importRooms() {
        csvRooms = getRoomsFromFile();
        for (Room importRoom : csvRooms) {
            validateStars(importRoom.getStars());
            try {
                findById(importRoom.getId());
                updateRoom(importRoom);
            } catch (ServiceException ex) {
                roomDao.createWithId(new Room(importRoom.getId(), importRoom.getNumber(), importRoom.getCost(),
                        importRoom.getCapacity(), importRoom.getStars(), importRoom.isRepaired()));
            }
        }
    }

    private List<Room> getRoomsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(ROOMS_PATH);
        return csvParser.parseRooms(lines);
    }

    @Override
    public void exportRoom(Long id) {
        Room room = findById(id);
        Room exportRoom = csvRooms.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        if (exportRoom == null) {
            csvRooms.add(room);
        } else {
            updateExportRoom(room, exportRoom);
        }
        List<String> lines = csvParser.parseRoomsToLines(csvRooms);
        fileWriter.writeResourceFileLines(ROOMS_PATH, lines);
    }

    private void updateExportRoom(Room room, Room exportRoom) {
        exportRoom.setNumber(room.getNumber());
        exportRoom.setCapacity(room.getCapacity());
        exportRoom.setCost(room.getCost());
        exportRoom.setStars(room.getStars());
        exportRoom.setRepaired(room.isRepaired());
    }

    private void validateStars(int stars) {
        if (stars < 1 || stars > 5) {
            throw new ServiceException("Star can not be less then 1 and more than 5");
        }
    }

    @Override
    public void updateStatus(Long id) {
        Room room = findById(id);
        room.setRepaired(!room.isRepaired());
        updateRoom(room);
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Room room = findById(id);
        room.setCost(cost);
        updateRoom(room);
    }

    @Override
    public Room findById(Long id) {
        for (Room room : roomDao.findAll()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        throw new ServiceException("There is not room with this id " + id);
    }

    private void updateRoom(Room room){
        roomDao.update(new Room(room.getId(), room.getNumber(), room.getCost(),
                room.getCapacity(), room.getStars(), room.isRepaired()));
    }

    @Override
    public List<Room> findAll() {
        return roomDao.findAll();
    }
}
