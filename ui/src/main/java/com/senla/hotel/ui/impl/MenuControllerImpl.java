package com.senla.hotel.ui.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Log;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.ui.*;
import org.apache.logging.log4j.Logger;

@Singleton
public class MenuControllerImpl implements MenuController {

    @Log
    private Logger log;
    @InjectByType
    private Builder builder;
    @InjectByType
    private Navigator navigator;

    @Override
    public void run() {
        log.info("Start app");
        System.out.print("Hotel");
        navigator.setCurrentMenu(builder.getRootMenu());
        while (navigator.getCurrentMenu() != null) {
            navigator.printMenu();
            int commandNumber = ConsoleReader.readNumber();
            navigator.navigate(commandNumber);
            chooseNextMenu(commandNumber);
        }
        log.info("End app");
    }

    private void chooseNextMenu(int commandNumber) {
        MenuItem n = navigator.getCurrentMenu().getMenuItems().get(commandNumber);
        if (n != null) {
            navigator.setCurrentMenu(n.getNextMenu());
        }
    }
}