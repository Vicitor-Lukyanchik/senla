package com.senla.hotel.dao;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.ServiceOrder;

import java.time.LocalDate;
import java.util.List;

public interface ServiceOrderDao {
    void create(LocalDate date, Long lodgerId, Long serviceId);
    void createWithId(Long id, LocalDate date, Long lodgerId, Long serviceId);
    void update(Long id, LocalDate date, Long lodgerId, Long serviceId);
    List<ServiceOrder> findAll();
}
