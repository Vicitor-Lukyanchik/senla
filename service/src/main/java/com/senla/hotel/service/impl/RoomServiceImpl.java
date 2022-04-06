package com.senla.hotel.service.impl;

import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.connection.hibernate.HibernateUtil;
import com.senla.hotel.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RoomServiceImpl implements RoomService {

    private static final String ROOMS_PATH = "csv/rooms.csv";

    @Autowired
    private FileReader fileReader;
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private FileWriter fileWriter;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private HibernateUtil hibernateUtil;
    private List<Room> csvRooms = new ArrayList<>();

    @Override
    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired) {
        validateStars(stars);
            Session session = hibernateUtil.getSession();
            Transaction transaction = session.beginTransaction();
        try {
            roomDao.create(new Room(number, cost, capacity, stars), session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
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
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDao.create(importRoom, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
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
    }

    private void validateStars(int stars) {
        if (stars < 1 || stars > 5) {
            String message = "Star can not be less then 1 and more than 5";
            log.error(message);
            throw new ServiceException(message);
        }
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Room room = findById(id);
        room.setCost(cost);
        update(room);
    }

    private void update(Room room){
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            roomDao.update(room, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public Room findById(Long id) {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        Room room = roomDao.findById(session, id);
        if (room == null){
            throw new ServiceException("There is not room with this id " + id);
        }
        return room;
    }

    @Override
    public List<Room> findAll() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        roomDao.setType(Room.class);
        List<Room> result = roomDao.findAll(session);
        session.close();
        return result;
    }
}
