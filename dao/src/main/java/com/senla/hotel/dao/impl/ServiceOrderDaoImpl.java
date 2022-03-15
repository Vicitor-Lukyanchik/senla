package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.ServiceOrderDao;
import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.Date;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Component
@Scope("singleton")
public class ServiceOrderDaoImpl extends EntityDao<ServiceOrder, Long> implements ServiceOrderDao {

    private static final String SERVICE_ORDER_TABLE = "service_orders";
    private static final String SERVICE_ORDER_SEQUENCE = "nextval('service_orders_id_seq')";
    private static final String INSERT_SERVICE_ORDER = "INSERT INTO " + SERVICE_ORDER_TABLE +
            "(" + SERVICE_ORDER_ID + ", " + SERVICE_ORDER_DATE +
            ", " + SERVICE_ORDER_LODGER_ID + ", " + SERVICE_ORDER_SERVICE_ID + ") " +
            "VALUES (" + SERVICE_ORDER_SEQUENCE + ", :date, :lodger_id, :service_id)";

    public void create(ServiceOrder serviceOrder, Session session) {
        try {
            Query query = session.createSQLQuery(INSERT_SERVICE_ORDER)
                    .setParameter("date", Date.valueOf(serviceOrder.getDate()))
                    .setParameter("lodger_id", serviceOrder.getLodgerId())
                    .setParameter("service_id", serviceOrder.getServiceId());
            query.executeUpdate();
        } finally {
            throw new DAOException("Can not create service order");
        }
    }
}
