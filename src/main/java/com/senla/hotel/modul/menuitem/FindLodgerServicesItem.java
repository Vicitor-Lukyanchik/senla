package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.FindLodgerServices;

public class FindLodgerServicesItem implements MenuItem {

    private static final String TITLE = "Find the services that lodger have gotten";

    private Action action;
    private Menu nextMenu;

    public FindLodgerServicesItem(Hotel hotel, Menu nextMenu) {
        action = new FindLodgerServices(hotel);
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
