package com.senla.hotel;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.MenuController;
import com.senla.hotel.modul.impl.MenuControllerImpl;

public class Application {

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        MenuController menuController = new MenuControllerImpl(hotel);
        menuController.run();
    }
}
