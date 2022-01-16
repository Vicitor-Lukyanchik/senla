package com.senla.hotel.ui.itembuilder.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.repository.ServiceRepository;
import com.senla.hotel.serializer.Serializer;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.itembuilder.ExitItemsBuilder;

public class ExitItemsBuilderImpl implements ExitItemsBuilder {

    @InjectByType
    private LodgerRepository lodgerRepository;
    @InjectByType
    private RoomRepository roomRepository;
    @InjectByType
    private ServiceRepository serviceRepository;
    @InjectByType
    private ReservationRepository reservationRepository;
    @InjectByType
    private ServiceOrderRepository serviceOrderRepository;
    @InjectByType
    private Serializer serializer;
    private Integer commandNumber = 1;

    @Override
    public Map<Integer, MenuItem> buildExitItems() {
        Map<Integer, MenuItem> result = new LinkedHashMap<>();
        result.put(commandNumber++, createMenuItem("Save", save, null));
        result.put(commandNumber++, createMenuItem("Do not save", doNotSave, null));
        result.put(commandNumber++, createMenuItem("Drop saves", dropSaves, null));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    private Action save = () -> serializer.serialize();;

    private Action dropSaves = () -> {
        lodgerRepository.setLodgers(new ArrayList<>());
        roomRepository.setRooms(new ArrayList<>());
        reservationRepository.setReservations(new ArrayList<>());
        serviceRepository.setServices(new ArrayList<>());
        serviceOrderRepository.setServiceOrders(new ArrayList<>());
        serializer.serialize();
    };

    private Action doNotSave = () -> {
    };
}
