package com.senla.hotel;

import com.senla.hotel.infrastucture.Application;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.ui.MenuController;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

public class ApplicationStarter {


    public static void main(String[] args) {
        ApplicationContext context = Application.run("com.senla.hotel");
        MenuController menuController = context.getObject(MenuController.class);
        menuController.run();
    }
}
