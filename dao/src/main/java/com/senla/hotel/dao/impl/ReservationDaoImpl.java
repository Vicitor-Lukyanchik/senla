package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.ReservationDao;
import com.senla.hotel.domain.Reservation;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.Date;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Repository
public class ReservationDaoImpl extends EntityDao<Reservation, Long> implements ReservationDao {

}
