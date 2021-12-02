package com.senla.hotel.modul.impl;

import java.util.Map;

import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.Navigator;
import com.senla.hotel.modul.menuitem.MenuItem;

public class NavigatorImpl implements Navigator {

    private Menu currentMenu;

    public NavigatorImpl(Menu currentMenu) {
        this.currentMenu = currentMenu;
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
            System.out.println("\nSuccess");
        } catch (IllegalArgumentException ex) {
            System.out.println("\nError");
        }
    }
}
