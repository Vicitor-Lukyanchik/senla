package com.senla.hotel.ui.itembuilder;

import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;

import java.util.Map;

public interface RoomItemsBuilder {
    Map<Integer, MenuItem> buildRoomItems(Menu rootMenu);
}
