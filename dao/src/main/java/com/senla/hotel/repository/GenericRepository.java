package com.senla.hotel.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericRepository<T, PK extends Serializable>{
    void create(T entity);

    void update(T entity);

    List<T> findAll(Class clazz);

    T findById(Class clazz, Long id);
}
