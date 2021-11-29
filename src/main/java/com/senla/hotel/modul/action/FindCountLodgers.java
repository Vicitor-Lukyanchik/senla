package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindCountLodgers implements Action {

    private LodgerService lodgerService;

    public FindCountLodgers(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.println("\nCount lodgers : " + lodgerService.findAll().size());
    }

}
