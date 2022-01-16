package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.repository.LodgerRepository;

@Singleton
public class LodgerRepositoryImpl implements LodgerRepository {

    private List<Lodger> lodgers = new ArrayList<>();

    public List<Lodger> getLodgers() {
        return new ArrayList<>(lodgers);
    }

    public void addLodger(Lodger lodger) {
        lodgers.add(lodger);
    }

    public void setLodgers(List<Lodger> lodgers) {
        this.lodgers = lodgers;
    }
}
