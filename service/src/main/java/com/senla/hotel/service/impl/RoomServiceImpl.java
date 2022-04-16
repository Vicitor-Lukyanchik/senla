package com.senla.hotel.service.impl;

import com.senla.hotel.entity.Room;
import com.senla.hotel.file.CsvFileReader;
import com.senla.hotel.file.CsvFileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoomServiceImpl implements RoomService {

    private static final String ROOMS_PATH = "rooms.csv";

    private final CsvFileReader fileReader;
    private final CsvParser csvParser;
    private final CsvFileWriter fileWriter;
    private final RoomRepository roomRepository;

    private List<Room> csvRooms = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void importRooms() {
        csvRooms = getRoomsFromFile();
        for (Room importRoom : csvRooms) {
            validateStars(importRoom.getStars());
            try {
                findById(importRoom.getId());
                update(importRoom);
            } catch (ServiceException e) {
                create(importRoom);
            }
        }
    }

    @Override
    @Transactional
    public void create(Room room) {
        validateStars(room.getStars());
        roomRepository.create(room);
    }

    private List<Room> getRoomsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(ROOMS_PATH);
        return csvParser.parseRooms(lines);
    }

    @Override
    @Transactional
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
    @Transactional
    public void updateCost(Long id, BigDecimal cost) {
        Room room = findById(id);
        room.setCost(cost);
        update(room);
    }

    @Transactional
    public void update(Room room) {
        roomRepository.update(room);
    }

    @Override
    @Transactional
    public Room findById(Long id) {
        Room room = roomRepository.findById(Room.class, id);
        if (room == null) {
            throw new ServiceException("There is not room with this id " + id);
        }
        return room;
    }

    @Override
    @Transactional
    public List<Room> findAll() {
        List<Room> result = roomRepository.findAll(Room.class);
        return result;
    }
}
