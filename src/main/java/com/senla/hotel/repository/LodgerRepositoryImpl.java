package com.senla.hotel.repository;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Lodger;

public class LodgerRepositoryImpl implements LodgerRepository {

    private static LodgerRepository instance;
    private List<Lodger> lodgers = new ArrayList<>();

    public static LodgerRepository getInstance() {
        if (instance == null) {
            instance = new LodgerRepositoryImpl();
        }
        return instance;
    }

    public List<Lodger> getLodgers() {
        return lodgers;
    }

    public void addLodger(Lodger lodger) {
        lodgers.add(lodger);
    }
}
