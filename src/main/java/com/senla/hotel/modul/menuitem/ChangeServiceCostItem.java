package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.ChangeServiceCost;

public class ChangeServiceCostItem implements MenuItem {

    private static final String TITLE = "Change service cost";

    private Action action;
    private Menu nextMenu;

    public ChangeServiceCostItem(Hotel hotel, Menu nextMenu) {
        action = new ChangeServiceCost(hotel);
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
