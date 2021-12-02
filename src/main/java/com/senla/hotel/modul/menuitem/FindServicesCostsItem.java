package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindServicesCosts;

public class FindServicesCostsItem implements MenuItem {

    private static final String TITLE = "Find services costs";

    private Action action;
    private Menu nextMenu;

    public FindServicesCostsItem(Hotel hotel, Menu nextMenu) {
        action = new FindServicesCosts(hotel);
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
