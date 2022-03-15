package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.RoomDao;
import com.senla.hotel.domain.Room;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Component
@Scope("singleton")
public class RoomDaoImpl extends EntityDao<Room, Long> implements RoomDao {

    private static final String ROOM_TABLE = "rooms";
    private static final String ROOM_SEQUENCE = "nextval('rooms_id_seq')";
    private static final String INSERT_ROOM = "INSERT INTO " + ROOM_TABLE +
            "(" + ROOM_ID + "," + ROOM_NUMBER + "," + ROOM_COST + "," + ROOM_CAPACITY +
            "," + ROOM_STARS + ") " +
            "VALUES (" + ROOM_SEQUENCE + ", :number, :cost, :capacity, :stars)";

    @Override
    public void create(Room room, Session session) {
        try {
            Query query = session.createSQLQuery(INSERT_ROOM)
                    .setParameter("number", room.getNumber())
                    .setParameter("cost", room.getCost())
                    .setParameter("capacity", room.getCapacity())
                    .setParameter("stars", room.getStars());
            query.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Can not create room");
        }
    }
}
