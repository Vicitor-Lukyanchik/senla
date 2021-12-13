package com.senla.hotel.filerepository;

import com.senla.hotel.domain.Service;

public interface ServiceFileRepository {
    Service findById(Long id);
    
    void export(Service service);
}
