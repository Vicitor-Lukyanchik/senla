package com.senla.hotel.repository;

import java.util.List;

import com.senla.hotel.domain.Lodger;

public interface LodgerRepository {
    List<Lodger> getLodgers();

    void addLodger(Lodger lodger);

    void setLodgers(List<Lodger> lodgers);
}
