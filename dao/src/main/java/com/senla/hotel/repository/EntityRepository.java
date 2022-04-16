package com.senla.hotel.repository;

import com.senla.hotel.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class EntityRepository<T, PK extends Serializable> implements GenericRepository<T, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(T entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            throw new DAOException("Can not create with id in " + entity.getClass().getSimpleName());
        }
    }

    @Override
    public void update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            throw new DAOException("Can not update " + entity.getClass().getSimpleName());
        }
    }

    @Override
    public List<T> findAll(Class clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public T findById(Class clazz, Long id) {
        try {
            return (T) entityManager.find(clazz, id);
        } catch (Exception e) {
            throw new DAOException("Can not find by id : " + id);
        }
    }
}
