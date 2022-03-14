package com.senla.hotel.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable>{
    void create(T entity, Session session);

    void createWithId(T entity, Session session);

    void update(T entity, Session session);

    List<T> findAll(Session session, Class<T> type);
}
