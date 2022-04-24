package com.senla.hotel.repository;

import com.senla.hotel.entity.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User, Long> {

    List<User> findByUsername(String username);
}
