package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.service.RoomService;

@Singleton
public class RoomServiceImpl implements RoomService {

    private static final String PATH = "rooms.csv";

    private final FileReader fileReader = ApplicationContext.getInstance().getObject(FileReader.class);
    private final CsvParser csvParser = ApplicationContext.getInstance().getObject(CsvParser.class);
    private final FileWriter fileWriter = ApplicationContext.getInstance().getObject(FileWriter.class);

    private RoomRepository roomRepository = ApplicationContext.getInstance().getObject(RoomRepository.class);
    private Long id = 0l;
    private List<Room> importRooms = new ArrayList<>();

    @Override
    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired) {
        validateStars(stars);
        roomRepository.addRoom(new Room(generateId(), number, cost, capacity, stars, isRepaired));
        id++;
    }

    private Long generateId() {
        try {
            while (true) {
                id++;
                findById(id);
            }
        } catch (ServiceException ex) {
            return id;
        }
    }

    @Override
    public void importRooms() {
        importRooms = getRoomsFromFile();
        for(Room importRoom : importRooms) {
            validateStars(importRoom.getStars());
            try {
                Room room = findById(id);
                room.setNumber(importRoom.getNumber());
                room.setCost(importRoom.getCost());
                room.setCapacity(importRoom.getCapacity());
                room.setStars(importRoom.getStars());
                room.setRepaired(importRoom.isRepaired());
            } catch (ServiceException ex) {
                roomRepository.addRoom(new Room(importRoom.getId(), importRoom.getNumber(), importRoom.getCost(), importRoom.getCapacity(),
                        importRoom.getStars(), importRoom.isRepaired()));
            }
        }
    }

    private List<Room> getRoomsFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return csvParser.parseRooms(lines);
    }
    
    @Override
    public void exportRoom(Long id) {
            Room room = findById(id);
            Room importRoom = importRooms.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
            if(importRoom == null) {
                importRooms.add(room);
            } else {
                importRoom.setNumber(room.getNumber());
                importRoom.setCapacity(room.getCapacity());
                importRoom.setCost(room.getCost());
                importRoom.setStars(room.getStars());
                importRoom.setRepaired(room.isRepaired());
            }
        List<String> lines = csvParser.parseRoomsToLines(importRooms);
        fileWriter.writeResourceFileLines(PATH, lines);
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
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Room room = findById(id);
        room.setCost(cost);
    }

    @Override
    public Room findById(Long id) {
        for (Room room : roomRepository.getRooms()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        throw new ServiceException("There is not room with this number");
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.getRooms();
    }
}
