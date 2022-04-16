package com.senla.hotel.repository.impl;

import com.senla.hotel.repository.EntityRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryImpl extends EntityRepository<Reservation, Long> implements ReservationRepository {

}
