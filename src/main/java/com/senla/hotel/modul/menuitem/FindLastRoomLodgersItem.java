package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindLastRoomLodgers;

public class FindLastRoomLodgersItem implements MenuItem {

    private static final String TITLE = "Find last room lodgers and their start date";

    private Action action;
    private Menu nextMenu;

    public FindLastRoomLodgersItem(Hotel hotel, Menu nextMenu) {
        action = new FindLastRoomLodgers(hotel);
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
