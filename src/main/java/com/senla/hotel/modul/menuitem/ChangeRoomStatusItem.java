package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.ChangeRoomStatus;

public class ChangeRoomStatusItem implements MenuItem {

    private static final String TITLE = "Change room status";

    private Action action;
    private Menu nextMenu;

    public ChangeRoomStatusItem(Hotel hotel, Menu nextMenu) {
        action = new ChangeRoomStatus(hotel);
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
