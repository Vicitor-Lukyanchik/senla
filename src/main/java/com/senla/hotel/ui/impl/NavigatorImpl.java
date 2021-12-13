package com.senla.hotel.ui.impl;

import java.time.format.DateTimeParseException;
import java.util.Map;

import com.senla.hotel.exception.FileException;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.Navigator;

public class NavigatorImpl implements Navigator {

    private static Navigator instance;

    private Menu currentMenu;

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new NavigatorImpl();
        }
        return instance;
    }

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
        } catch (ServiceException ex) {
            System.out.println("\nError");
            System.out.println(ex.getMessage());
        } catch (DateTimeParseException ex) {
            System.out.println("\nError");
            System.out.println("Date should be this format : DD.MM.YYYY");
        } catch (FileRepositoryException ex) {
            System.out.println("\nError");
            System.out.println(ex.getMessage());
        } catch (FileException ex) {
            System.out.println("\nError");
            System.out.println(ex.getMessage());
        }
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
}
