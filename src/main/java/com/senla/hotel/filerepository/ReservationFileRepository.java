package com.senla.hotel.filerepository;

import com.senla.hotel.domain.Reservation;

public interface ReservationFileRepository {
    Reservation findById(Long id);
    
    void export(Reservation reservation);
}
