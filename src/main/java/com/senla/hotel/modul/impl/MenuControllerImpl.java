package com.senla.hotel.modul.impl;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Builder;
import com.senla.hotel.modul.MenuController;
import com.senla.hotel.modul.Navigator;
import com.senla.hotel.reader.ConsoleReader;

public class MenuControllerImpl implements MenuController {

    private Builder builder;
    private Navigator navigator;

    public MenuControllerImpl(Hotel hotel) {
        this.builder = new BuilderImpl(hotel);
        this.navigator = new NavigatorImpl(builder.getRootMenu());
    }

    @Override
    public void run() {
        System.out.print("Hotel");
        while(true) {
            navigator.printMenu();
            int commandNumber = ConsoleReader.readNumber();
            navigator.navigate(commandNumber);
            navigator = new NavigatorImpl(builder.getRootMenu().getMenuItems().get(commandNumber).getNextMenu());
        }
    }
}
