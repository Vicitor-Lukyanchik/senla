package com.senla.hotel.ui.action;

import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindCountLodgers implements Action {

    private LodgerService lodgerService;

    public FindCountLodgers() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("\nCount lodgers : " + lodgerService.findAll().size());
    }

}
