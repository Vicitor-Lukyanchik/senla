package com.senla.hotel.ui.impl;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.MenuController;
import com.senla.hotel.ui.Navigator;

public class MenuControllerImpl implements MenuController {

    private Builder builder;
    private Navigator navigator;

    public MenuControllerImpl() {
        this.builder = BuilderImpl.getInstance();
        this.navigator = NavigatorImpl.getInstance();
    }

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
