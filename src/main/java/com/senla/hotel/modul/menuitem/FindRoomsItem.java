package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindRooms;

public class FindRoomsItem implements MenuItem {

    private static final String TITLE = "Find all rooms";

    private Action action;
    private Menu nextMenu;

    public FindRoomsItem(Hotel hotel, Menu nextMenu) {
        action = new FindRooms(hotel);
        this.nextMenu = nextMenu;
    }

    @Override
    public void doAction() {
        action.execute();
    }

    @Override
    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }
}
