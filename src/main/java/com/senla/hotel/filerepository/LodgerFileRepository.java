package com.senla.hotel.filerepository;

import com.senla.hotel.domain.Lodger;

public interface LodgerFileRepository {
    Lodger findById(Long id);
    
    void export(Lodger lodger);
}
