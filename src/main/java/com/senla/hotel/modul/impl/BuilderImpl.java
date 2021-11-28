package com.senla.hotel.modul.impl;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Builder;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.menuitem.AddLodgerItem;
import com.senla.hotel.modul.menuitem.AddRoomItem;
import com.senla.hotel.modul.menuitem.AddServiceItem;
import com.senla.hotel.modul.menuitem.MenuItem;
import com.senla.hotel.modul.menuitem.OrderServiceToLodgerItem;
import com.senla.hotel.modul.menuitem.PutLodgerInRoomItem;

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
        menuItems.put(1, new AddRoomItem(hotel, rootMenu));
        menuItems.put(2, new AddServiceItem(hotel, rootMenu));
        menuItems.put(3, new AddLodgerItem(hotel, rootMenu));
        menuItems.put(4, new PutLodgerInRoomItem(hotel, rootMenu));
        menuItems.put(5, new OrderServiceToLodgerItem(hotel, rootMenu));
        rootMenu.setMenuItems(menuItems);
    }

    @Override
    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
