package com.senla.hotel.modul.menuitem;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.modul.Menu;
import com.senla.hotel.modul.action.Action;
import com.senla.hotel.modul.action.EvictLodgerFromRoom;

public class EvictLodgerFromRoomItem implements MenuItem {

    private static final String TITLE = "Evict lodger from room";

    private Action action;
    private Menu nextMenu;

    public EvictLodgerFromRoomItem(Hotel hotel, Menu nextMenu) {
        action = new EvictLodgerFromRoom(hotel);
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
