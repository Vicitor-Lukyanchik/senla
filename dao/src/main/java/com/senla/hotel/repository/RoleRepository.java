package com.senla.hotel.repository;

import com.senla.hotel.entity.Role;

import java.util.List;

public interface RoleRepository extends GenericRepository<Role, Long> {
    List<Role> findByName(String role_user);
}
