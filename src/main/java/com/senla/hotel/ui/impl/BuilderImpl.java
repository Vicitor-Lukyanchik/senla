package com.senla.hotel.ui.impl;

import java.util.HashMap;
import java.util.Map;

import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.menuitem.MenuItem;

public class BuilderImpl implements Builder {

    private static Builder instance;
    
    private Menu rootMenu = null;
    private Menu roomMenu = null;
    private Menu serviceMenu = null;
    private Menu lodgerMenu = null;

    public static Builder getInstance() {
        if(instance == null) {
            instance = new BuilderImpl();
        }
        return instance;
    }
    
    private void buildMenu() {
        roomMenu = new Menu();
        roomMenu.setName("Root menu");
        Map<Integer, MenuItem> roomMenuItems = new HashMap<>();     
        roomMenuItems.put(1, new MenuItem("", rootMenu));    
        roomMenu.setMenuItems(roomMenuItems);
        
        serviceMenu = new Menu();
        serviceMenu.setName("Service menu");
        Map<Integer, MenuItem> serviceMenuItems = new HashMap<>();
        roomMenuItems.put(1, new MenuItem("", rootMenu));    
        serviceMenu.setMenuItems(serviceMenuItems);
        
        lodgerMenu = new Menu();
        lodgerMenu.setName("Lodger menu");
        Map<Integer, MenuItem> lodgerMenuItems = new HashMap<>();
        roomMenuItems.put(1, new MenuItem("", rootMenu));    
        lodgerMenu.setMenuItems(lodgerMenuItems);
        
        rootMenu = new Menu();
        rootMenu.setName("Root menu");
        Map<Integer, MenuItem> rootMenuItems = new HashMap<>();
        rootMenuItems.put(1, new MenuItem("Commands with rooms", roomMenu));
        rootMenuItems.put(2, new MenuItem("Commands with services", roomMenu));
        rootMenuItems.put(3, new MenuItem("Commands with lodgers", roomMenu));
        rootMenuItems.put(4, new MenuItem("Exit", null));
        rootMenu.setMenuItems(rootMenuItems);
    }

    @Override
    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
