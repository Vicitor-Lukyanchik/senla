package com.senla.hotel.ui.impl;

import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.ConsoleReader;
import com.senla.hotel.ui.MenuController;
import com.senla.hotel.ui.Navigator;

public class MenuControllerImpl implements MenuController {

    private Builder builder = ApplicationContext.getInstance().getObject(Builder.class);
    private Navigator navigator = ApplicationContext.getInstance().getObject(Navigator.class);

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
