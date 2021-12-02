package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindRoomsCosts;

public class FindRoomsCostsItem implements MenuItem {

    private static final String TITLE = "Find rooms costs";

    private Action action;
    private Menu nextMenu;

    public FindRoomsCostsItem(Hotel hotel, Menu nextMenu) {
        action = new FindRoomsCosts(hotel);
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
