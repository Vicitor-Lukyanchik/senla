package com.senla.hotel.repository.impl;

import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.entity.Room;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryImpl extends EntityRepository<Room, Long> implements RoomRepository {

}
