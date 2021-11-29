package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.ChangeRoomCost;

public class ChangeRoomCostItem implements MenuItem {

    private static final String TITLE = "Change room cost";

    private Action action;
    private Menu nextMenu;

    public ChangeRoomCostItem(Hotel hotel, Menu nextMenu) {
        action = new ChangeRoomCost(hotel);
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
