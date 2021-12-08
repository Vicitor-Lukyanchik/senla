package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.impl.RoomRepositoryImpl;
import com.senla.hotel.service.RoomService;

public class RoomServiceImpl implements RoomService {

    private static RoomService instance;
    
    private RoomRepository roomRepository;
    private Long id = 1l;

    public RoomServiceImpl() {
        roomRepository = RoomRepositoryImpl.getInstance();
    }

    public static RoomService getInstance() {
        if(instance == null) {
            instance = new RoomServiceImpl();
        }
        return instance;
    }
    
    @Override
    public void create(int number, BigDecimal cost, int capacity, int stars, boolean isRepaired) {
        validateStars(stars);
        roomRepository.addRoom(new Room(id, number, cost, capacity, stars, isRepaired));
        id++;
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
