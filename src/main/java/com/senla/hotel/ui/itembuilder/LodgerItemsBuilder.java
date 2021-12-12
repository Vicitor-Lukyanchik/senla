package com.senla.hotel.ui.itembuilder;

import java.util.Map;

import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;

public interface LodgerItemsBuilder {
    Map<Integer, MenuItem> buildLodgerItems(Menu rootMenu);
}
