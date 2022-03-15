package com.senla.hotel.ui.impl;

import com.senla.hotel.ui.Builder;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.itembuilder.LodgerItemsBuilder;
import com.senla.hotel.ui.itembuilder.RoomItemsBuilder;
import com.senla.hotel.ui.itembuilder.ServiceItemsBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
@Log4j2
public class BuilderImpl implements Builder {

    private Menu rootMenu = null;
    @Autowired
    private Menu roomMenu;
    @Autowired
    private Menu serviceMenu;
    @Autowired
    private Menu lodgerMenu;
    @Autowired
    private RoomItemsBuilder roomItemsBuilder;
    @Autowired
    private ServiceItemsBuilder serviceItemsBuilder;
    @Autowired
    private LodgerItemsBuilder lodgerItemsBuilder;

    @Override
    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }

    private void buildMenu() {
        buildRootMenu();
        buildRoomMenu();
        buildServiceMenu();
        buildLodgerMenu();
    }

    private void buildRootMenu() {
        rootMenu = new Menu();
        rootMenu.setName("Root menu");
        Map<Integer, MenuItem> rootMenuItems = new HashMap<>();
        rootMenuItems.put(1, new MenuItem("Commands with rooms", roomMenu));
        rootMenuItems.get(1).setAction(() -> {
        });
        rootMenuItems.put(2, new MenuItem("Commands with services", serviceMenu));
        rootMenuItems.get(2).setAction(() -> {
        });
        rootMenuItems.put(3, new MenuItem("Commands with lodgers", lodgerMenu));
        rootMenuItems.get(3).setAction(() -> {
        });
        rootMenuItems.put(4, new MenuItem("Exit", null));
        rootMenuItems.get(4).setAction(() -> {
        });
        rootMenu.setMenuItems(rootMenuItems);
    }

    private void buildRoomMenu() {
        roomMenu.setName("Room menu");
        roomMenu.setMenuItems(roomItemsBuilder.buildRoomItems(rootMenu));
    }

    private void buildServiceMenu() {
        serviceMenu.setName("Service menu");
        serviceMenu.setMenuItems(serviceItemsBuilder.buildServiceItems(rootMenu));
    }

    private void buildLodgerMenu() {
        lodgerMenu.setName("Lodger menu");
        lodgerMenu.setMenuItems(lodgerItemsBuilder.buildLodgerItems(rootMenu));
    }
}
