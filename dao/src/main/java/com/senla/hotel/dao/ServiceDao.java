package com.senla.hotel.dao;

import com.senla.hotel.domain.Service;
import org.hibernate.Session;

import java.util.List;

public interface ServiceDao extends GenericDao<Service, Long> {
    void create(Service entity, Session session);

    void update(Service entity, Session session);

    List<Service> findAll(Session session, Class<Service> type);
}
