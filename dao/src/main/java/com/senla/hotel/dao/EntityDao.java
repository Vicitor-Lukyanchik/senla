package com.senla.hotel.dao;

import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class EntityDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;

    @Override
    public void create(T entity, Session session) {
        try {
            session.save(entity);
        } catch (Exception e) {
            throw new DAOException("Can not create with id in " + entity.getClass().getSimpleName());
        }
    }

    @Override
    public void update(T entity, Session session) {
        try {
            session.update(entity);
        } catch (Exception e) {
            throw new DAOException("Can not update " + entity.getClass().getSimpleName());
        }
    }

    @Override
    public List<T> findAll(Session session) {
        try {
            CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(type);
            Root<T> root = query.from(type);
            CriteriaQuery<T> select = query.select(root);
            return session.createQuery(select).getResultList();
        } catch (Exception e) {
            throw new DAOException("Can not find in " + type.getSimpleName());
        }
    }

    @Override
    public void setType(Class<T> type) {
        this.type = type;
    }

    public T findById(Session session, Long id) {
        try {
            return session.get(type, id);
        } catch (Exception e) {
            throw new DAOException("Can not find by id : " + id);
        }
    }
}
