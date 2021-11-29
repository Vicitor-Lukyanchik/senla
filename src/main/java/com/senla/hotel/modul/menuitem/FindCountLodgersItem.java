package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindCountLodgers;

public class FindCountLodgersItem implements MenuItem {

    private static final String TITLE = "Find count lodgers";

    private Action action;
    private Menu nextMenu;

    public FindCountLodgersItem(Hotel hotel, Menu nextMenu) {
        action = new FindCountLodgers(hotel);
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
