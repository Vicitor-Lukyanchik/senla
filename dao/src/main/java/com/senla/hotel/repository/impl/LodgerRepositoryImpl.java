package com.senla.hotel.repository.impl;

import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.entity.Lodger;
import org.springframework.stereotype.Repository;

@Repository
public class LodgerRepositoryImpl extends EntityRepository<Lodger, Long> implements LodgerRepository {

}
