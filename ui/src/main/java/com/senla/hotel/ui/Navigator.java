package com.senla.hotel.ui;

public interface Navigator {

    void printMenu();

    void navigate(int index);

    void setCurrentMenu(Menu currentMenu);

    Menu getCurrentMenu();
}
