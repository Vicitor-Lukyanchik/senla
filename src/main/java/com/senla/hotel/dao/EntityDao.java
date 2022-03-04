package com.senla.hotel.dao;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class EntityDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    @Override
    public void create(T entity, Session session) {

    }

    @Override
    public void createWithId(T entity, Session session) {
        session.save(entity);
    }

    @Override
    public void update(T entity, Session session) {
        session.update(entity);
    }

    @Override
    public List<T> findAll(Session session, Class<T> type) {
        CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(type);
        Root<T> root = query.from(type);
        CriteriaQuery<T> select = query.select(root);
        return session.createQuery(select).getResultList();
    }
}
