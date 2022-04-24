package com.senla.hotel.repository.impl;

import com.senla.hotel.entity.User;
import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl  extends EntityRepository<User, Long> implements UserRepository {

    private static final String FIND_BY_USERNAME_QUERY = "SELECT u FROM users u WHERE u.username = :username";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByUsername(String username) {
        Query query = entityManager.createQuery(FIND_BY_USERNAME_QUERY);
        query.setParameter("username", username);
        return query.getResultList();
    }
}
