package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;

import javax.persistence.Query;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Singleton
public class LodgerDao extends EntityDao<Lodger, Long> {

    private static final String LODGER_TABLE = "lodgers";
    private static final String LODGER_SEQUENCE = "nextval('lodgers_id_seq')";
    private static final String INSERT_LODGER = "INSERT INTO " + LODGER_TABLE +
            "(" + LODGER_ID + "," + LODGER_FIRST_NAME + "," + LODGER_LAST_NAME + "," + LODGER_PHONE + ") VALUES (" +
            LODGER_SEQUENCE + ", :first_name, :last_name, :phone_number)";

    public void create(Lodger lodger, Session session) {
        try {
            Query query = session.createSQLQuery(INSERT_LODGER)
                    .setParameter("first_name", lodger.getFirstName())
                    .setParameter("last_name", lodger.getLastName())
                    .setParameter("phone_number", lodger.getPhoneNumber());
            query.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Can not create lodger");
        }
    }
}
