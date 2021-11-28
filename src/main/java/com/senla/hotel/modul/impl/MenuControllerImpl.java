package com.senla.hotel.modul.impl;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Builder;
import com.senla.hotel.modul.MenuController;
import com.senla.hotel.modul.Navigator;

public class MenuControllerImpl implements MenuController {

    private Hotel hotel;
    private Builder builder;
    private Navigator navigator;

    public MenuControllerImpl(Hotel hotel) {
        this.hotel = hotel;
        this.builder = new BuilderImpl(hotel);
        this.navigator = new NavigatorImpl(builder.getRootMenu());
    }

    @Override
    public void run() {
        
    }
}
