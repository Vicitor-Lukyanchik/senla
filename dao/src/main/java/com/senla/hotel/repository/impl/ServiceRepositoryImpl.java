package com.senla.hotel.repository.impl;

import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.ServiceRepository;
import com.senla.hotel.entity.Service;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceRepositoryImpl extends EntityRepository<Service, Long> implements ServiceRepository {

}
