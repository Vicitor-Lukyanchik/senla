package com.senla.hotel.repository.impl;

import com.senla.hotel.entity.Role;
import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends EntityRepository<Role, Long> implements RoleRepository {

    private static final String FIND_BY_NAME_QUERY = "select r from roles as r where roles.name=:name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findByName(String name) {
        Query query = entityManager.createQuery(FIND_BY_NAME_QUERY);
        return query.setParameter("name", name).getResultList();
    }
}
