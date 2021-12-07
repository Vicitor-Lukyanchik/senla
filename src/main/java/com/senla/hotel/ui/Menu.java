package com.senla.hotel.ui;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.ui.menuitem.MenuItem;

public class Menu {

    private String name;
    private Map<Integer, MenuItem> menuItems = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Map<Integer, MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
