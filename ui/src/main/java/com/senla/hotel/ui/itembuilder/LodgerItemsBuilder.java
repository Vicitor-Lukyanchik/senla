package com.senla.hotel.ui.itembuilder;

import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;

import java.util.Map;

public interface LodgerItemsBuilder {
    Map<Integer, MenuItem> buildLodgerItems(Menu rootMenu);
}
