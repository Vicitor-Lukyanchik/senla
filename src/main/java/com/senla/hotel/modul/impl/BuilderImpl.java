package com.senla.hotel.modul.impl;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Builder;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.menuitem.AddLodgerItem;
import com.senla.hotel.modul.menuitem.AddRoomItem;
import com.senla.hotel.modul.menuitem.AddServiceItem;
import com.senla.hotel.modul.menuitem.ChangeRoomCostItem;
import com.senla.hotel.modul.menuitem.ChangeRoomStatusItem;
import com.senla.hotel.modul.menuitem.ChangeServiceCostItem;
import com.senla.hotel.modul.menuitem.EvictLodgerFromRoomItem;
import com.senla.hotel.modul.menuitem.FindCountLodgersItem;
import com.senla.hotel.modul.menuitem.FindLodgersRoomsItem;
import com.senla.hotel.modul.menuitem.FindNotSettledRoomsItem;
import com.senla.hotel.modul.menuitem.FindNotSettledRoomsOnDateItem;
import com.senla.hotel.modul.menuitem.FindReservationCostItem;
import com.senla.hotel.modul.menuitem.FindRoomsItem;
import com.senla.hotel.modul.menuitem.FindCountNotSettledRoomsItem;
import com.senla.hotel.modul.menuitem.FindLodgerServicesItem;
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
        menuItems.put(6, new ChangeRoomStatusItem(hotel, rootMenu));
        menuItems.put(7, new ChangeRoomCostItem(hotel, rootMenu));
        menuItems.put(8, new ChangeServiceCostItem(hotel, rootMenu));
        menuItems.put(9, new EvictLodgerFromRoomItem(hotel, rootMenu));
        menuItems.put(10, new FindRoomsItem(hotel, rootMenu));
        menuItems.put(11, new FindNotSettledRoomsItem(hotel, rootMenu));
        menuItems.put(12, new FindLodgersRoomsItem(hotel, rootMenu));
        menuItems.put(13, new FindCountNotSettledRoomsItem(hotel, rootMenu));
        menuItems.put(14, new FindCountLodgersItem(hotel, rootMenu));
        menuItems.put(15, new FindNotSettledRoomsOnDateItem(hotel, rootMenu));
        menuItems.put(16, new FindReservationCostItem(hotel, rootMenu));
        menuItems.put(17, new FindLodgerServicesItem(hotel, rootMenu));
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
