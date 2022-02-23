package com.senla.hotel.service.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.dao.connection.Transaction;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.DAOException;
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
    @InjectByType
    private Transaction transaction;
    private List<Room> csvRooms = new ArrayList<>();

    @Override
    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired) {
        validateStars(stars);
        try {
            transaction.begin();
            roomDao.create(new Room(number, cost, capacity, stars, isRepaired), transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    @Override
    public void importRooms() {
        csvRooms = getRoomsFromFile();
        for (Room importRoom : csvRooms) {
            validateStars(importRoom.getStars());
            try {
                findById(importRoom.getId());
                update(importRoom);
            } catch (ServiceException e) {
                createWithId(importRoom);
            }
        }
    }

    private void createWithId(Room importRoom) {
        try {
            transaction.begin();
            roomDao.createWithId(importRoom, transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
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
        update(room);
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Room room = findById(id);
        room.setCost(cost);
        update(room);
    }

    private void update(Room room){
        try {
            transaction.begin();
            roomDao.update(room, transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    @Override
    public Room findById(Long id) {
        for (Room room : findAll()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        throw new ServiceException("There is not room with this id " + id);
    }

    @Override
    public List<Room> findAll() {
        transaction.begin();
        List<Room> result = roomDao.findAll(transaction.getConnection());
        transaction.end();
        return result;
    }
}
