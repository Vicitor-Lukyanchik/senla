package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.PutLodgerInRoom;

public class PutLodgerInRoomItem implements MenuItem {

    private static final String TITLE = "Put lodger in the room";

    private Action action;
    private Menu nextMenu;

    public PutLodgerInRoomItem (Hotel hotel, Menu nextMenu) {
        action = new PutLodgerInRoom(hotel);
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
