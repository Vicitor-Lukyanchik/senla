package com.senla.hotel.ui.impl;

import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;

import com.senla.hotel.annotation.Log;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.exception.FileException;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.exception.ValidatorException;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.Navigator;
import org.apache.logging.log4j.Logger;

@Singleton
public class NavigatorImpl implements Navigator {

    private Menu currentMenu;
    @Log
    private Logger log;

    @Override
    public void printMenu() {
        System.out.println("\n\n");
        for (Map.Entry<Integer, MenuItem> menuItem : currentMenu.getMenuItems().entrySet()) {
            System.out.println(menuItem.getKey() + ") " + menuItem.getValue().getTitle());
        }
        System.out.print("Press number of command : ");
    }

    @Override
    public void navigate(int index) {
        if (currentMenu.getMenuItems().containsKey(index)) {
            execute(index);
        } else {
            System.out.println("Menu item with index " + index + " not found");
        }
    }

    private void execute(int index) {
        try {
            currentMenu.getMenuItems().get(index).doAction();
        } catch (ServiceException | FileException | ValidatorException | DAOException e) {
            log.error(e.getMessage());
            System.out.println("\nError\n" + e.getMessage());
        } catch (DateTimeParseException ex) {
            String message = "Date should be this format : DD.MM.YYYY";
            log.error(message);
            System.out.println("\nError\n" + message);
        } catch (InputMismatchException ex) {
            String message = "Cost should be this format : SS,CC";
            log.error(message);
            System.out.println("\nError\n" + message);
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
}
