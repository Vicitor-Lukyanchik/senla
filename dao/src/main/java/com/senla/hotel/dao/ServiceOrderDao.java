package com.senla.hotel.dao;

import com.senla.hotel.domain.ServiceOrder;
import org.hibernate.Session;

import java.util.List;

public interface ServiceOrderDao {
    void create(ServiceOrder entity, Session session);

    void createWithId(ServiceOrder entity, Session session);

    void update(ServiceOrder entity, Session session);

    List<ServiceOrder> findAll(Session session, Class<ServiceOrder> type);
}
