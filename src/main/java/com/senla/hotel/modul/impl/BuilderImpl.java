package com.senla.hotel.modul.impl;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Builder;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.menuitem.AddRoomMenuItem;
import com.senla.hotel.modul.menuitem.MenuItem;

public class BuilderImpl implements Builder {

    private Hotel hotel;
    private Menu rootMenu = null;

    public BuilderImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    private void buildMenu() {
        rootMenu = new Menu();
        rootMenu.setName("Root menu");
        
        Map<Integer, MenuItem> menuItems = new HashMap<>();
        menuItems.put(1, new AddRoomMenuItem(hotel, rootMenu));
    }

    @Override
    public Menu getRootMenu() {
        if(rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
