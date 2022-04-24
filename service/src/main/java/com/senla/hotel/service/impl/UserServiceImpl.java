package com.senla.hotel.service.impl;

import com.senla.hotel.repository.RoleRepository;
import com.senla.hotel.repository.UserRepository;
import com.senla.hotel.entity.Role;
import com.senla.hotel.entity.Status;
import com.senla.hotel.entity.User;
import com.senla.hotel.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(User user) {
        List<Role> userRoles = roleRepository.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        userRepository.create(user);
        User registeredUser = findByUsername(user.getUsername());

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> result = userRepository.findAll(User.class);
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        roleRepository.findAll(Role.class);
        List<User> result = userRepository.findAll(User.class);

        if (result.isEmpty()) {
            log.warn("IN findByUsername - no user found by username: {}", username);
            return null;
        }

        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result.get(0);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        User result = userRepository.findById(User.class, id);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }
}
