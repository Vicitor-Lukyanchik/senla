package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindReservationCost;

public class FindReservationCostItem implements MenuItem {

    private static final String TITLE = "Find the cost that lodger should pay for the room";

    private Action action;
    private Menu nextMenu;

    public FindReservationCostItem(Hotel hotel, Menu nextMenu) {
        action = new FindReservationCost(hotel);
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
