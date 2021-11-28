package com.senla.hotel.modul.menuitem;

import com.senla.hotel.modul.Menu;

public interface MenuItem {

    void doAction();
    
    Menu getNextMenu();
    
    String getTitle();
}
