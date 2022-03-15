package com.senla.hotel.ui.impl;

import com.senla.hotel.ui.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Log4j2
public class MenuControllerImpl implements MenuController {

    @Autowired
    private Builder builder;
    @Autowired
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
