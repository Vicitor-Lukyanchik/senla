package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Date;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Component
@Scope("singleton")
public class ReservationDaoImpl extends EntityDao<Reservation, Long> implements ReservationDao {

    private static final String RESERVATION_TABLE = "reservations";
    private static final String RESERVATION_SEQUENCE = "nextval('reservations_id_seq')";
    private static final String INSERT_RESERVATION = "INSERT INTO " + RESERVATION_TABLE +
            "(" + RESERVATION_ID + "," + RESERVATION_START_DATE + "," + RESERVATION_END_DATE +
            "," + RESERVATION_LODGER_ID + "," + RESERVATION_ROOM_ID + "," + RESERVATION_RESERVED + ") " +
            "VALUES (" + RESERVATION_SEQUENCE + ", :start_date, :end_date, :lodger_id, :room_id, :reserved)";

    public void create(Reservation reservation, Session session) {
        try {
            Query query =  session.createSQLQuery(INSERT_RESERVATION)
                    .setParameter("start_date", Date.valueOf(reservation.getStartDate()))
                    .setParameter("end_date", Date.valueOf(reservation.getEndDate()))
                    .setParameter("lodger_id", reservation.getLodgerId())
                    .setParameter("room_id", reservation.getRoomId())
                    .setParameter("reservation", reservation.isReserved());
            query.executeUpdate();
        } finally {
            throw new DAOException("Can not create reservation");
        }
    }
}
