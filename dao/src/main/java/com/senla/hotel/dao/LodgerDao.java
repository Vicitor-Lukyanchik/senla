package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import org.hibernate.Session;

import java.util.List;

public interface LodgerDao extends GenericDao<Lodger, Long> {
    void create(Lodger entity, Session session);

    void update(Lodger entity, Session session);

    List<Lodger> findAll(Session session, Class<Lodger> type);
}
