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
        String choose = "";
        System.out.print("Hotel");
        while(!choose.equals("stop")) {
            navigator.printMenu();
            navigator.navigate(ConsoleReader.readNumber());
            System.out.print("Press enter to continue; To stop programm - stop : ");
            ConsoleReader.readLine();
            choose = ConsoleReader.readLine();
        }
    }
}
