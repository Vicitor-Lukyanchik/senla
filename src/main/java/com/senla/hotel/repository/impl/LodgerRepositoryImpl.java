package com.senla.hotel.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.repository.LodgerRepository;

public class LodgerRepositoryImpl implements LodgerRepository {

    private List<Lodger> lodgers = new ArrayList<>();

    public List<Lodger> getLodgers() {
        return new ArrayList<>(lodgers);
    }

    public void addLodger(Lodger lodger) {
        lodgers.add(lodger);
    }
}
