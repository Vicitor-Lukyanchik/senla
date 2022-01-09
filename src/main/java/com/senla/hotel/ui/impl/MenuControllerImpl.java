package com.senla.hotel.ui.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.ConsoleReader;
import com.senla.hotel.ui.MenuController;
import com.senla.hotel.ui.Navigator;

@Singleton
public class MenuControllerImpl implements MenuController {

    @InjectByType
    private Builder builder;
    @InjectByType
    private Navigator navigator;

    @Override
    public void run() {
        System.out.print("Hotel");
        navigator.setCurrentMenu(builder.getRootMenu());
        while (navigator.getCurrentMenu() != null) {
            navigator.printMenu();
            int commandNumber = ConsoleReader.readNumber();
            navigator.navigate(commandNumber);
            navigator.setCurrentMenu(navigator.getCurrentMenu().getMenuItems().get(commandNumber).getNextMenu());
        }
    }
}
