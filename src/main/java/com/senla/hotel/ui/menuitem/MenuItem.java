package com.senla.hotel.ui.menuitem;

import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.action.Action;

public class MenuItem {

    private final String title;

    private Action action;
    private Menu nextMenu;

    public MenuItem(String title, Menu nextMenu) {
        this.title = title;
        this.nextMenu = nextMenu;
    }

    public void doAction() {
        action.execute();
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
