package com.senla.hotel.repository.impl;

import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.entity.ServiceOrder;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceOrderRepositoryImpl extends EntityRepository<ServiceOrder, Long> implements ServiceOrderRepository {

}
