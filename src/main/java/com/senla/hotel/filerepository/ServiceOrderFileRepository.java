package com.senla.hotel.filerepository;

import com.senla.hotel.domain.ServiceOrder;

public interface ServiceOrderFileRepository {
    ServiceOrder findById(Long id);
    
    void export(ServiceOrder serviceOrder);
}
