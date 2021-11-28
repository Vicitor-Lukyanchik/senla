package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.AddRoom;

public class AddRoomMenuItem implements MenuItem {

    private static final String TITLE = "Add room";

    private Hotel hotel;
    private Action action = new AddRoom(hotel);
    private Menu nextMenu;

    public AddRoomMenuItem(Hotel hotel, Menu nextMenu) {
        this.hotel = hotel;
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
