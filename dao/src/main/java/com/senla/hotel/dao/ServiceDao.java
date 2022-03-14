package com.senla.hotel.dao;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;

import javax.persistence.Query;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Singleton
public class ServiceDao extends EntityDao<Service, Long> {

    private static final String SERVICE_TABLE = "services";
    private static final String SERVICE_SEQUENCE = "nextval('services_id_seq')";
    private static final String INSERT_SERVICE = "INSERT INTO " + SERVICE_TABLE + " ("
            + SERVICE_ID + ", " + SERVICE_NAME + ", " + SERVICE_COST + ") VALUES " +
            "(" + SERVICE_SEQUENCE + ", :name, :cost)";

    @Override
    public void create(Service entity, Session session) {
        try {
            Query query = session.createSQLQuery(INSERT_SERVICE)
                    .setParameter("name", entity.getName())
                    .setParameter("cost", entity.getCost());
            query.executeUpdate();
        } catch (Exception e){
            throw new DAOException("Can not create service");
        }
    }
}
