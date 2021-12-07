package com.senla.hotel;

import com.senla.hotel.ui.MenuController;
import com.senla.hotel.ui.impl.MenuControllerImpl;

public class Application {

    public static void main(String[] args) {
        MenuController menuController = new MenuControllerImpl();
        menuController.run();
    }
}
