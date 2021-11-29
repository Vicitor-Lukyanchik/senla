package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindCountNotSettledRooms;

public class FindCountNotSettledRoomsItem implements MenuItem {

    private static final String TITLE = "Find count not settled rooms";

    private Action action;
    private Menu nextMenu;

    public FindCountNotSettledRoomsItem(Hotel hotel, Menu nextMenu) {
        action = new FindCountNotSettledRooms(hotel);
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
