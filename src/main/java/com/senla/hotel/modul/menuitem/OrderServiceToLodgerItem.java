package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.OrderServiceToLodger;

public class OrderServiceToLodgerItem implements MenuItem {

    private static final String TITLE = "Order service to lodger";

    private Action action;
    private Menu nextMenu;

    public OrderServiceToLodgerItem(Hotel hotel, Menu nextMenu) {
        action = new OrderServiceToLodger(hotel);
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
