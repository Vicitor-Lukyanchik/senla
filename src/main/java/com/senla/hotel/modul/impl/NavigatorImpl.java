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
        for (Map.Entry<Integer, MenuItem> menuItem : currentMenu.getMenuItems().entrySet()) {
            System.out.println(menuItem.getKey() + ") " + menuItem.getValue().getTitle());
        }
    }

    @Override
    public void navigate(Integer index) {
        if (currentMenu.getMenuItems().containsKey(index)) {
            execute(index);
        }
        System.out.println("Menu item with index " + index + " not found");
    }

    private void execute(int index) {
        try {
            currentMenu.getMenuItems().get(index).doAction();
            System.out.println("Success");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error");
        }
    }
}
