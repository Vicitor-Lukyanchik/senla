package com.senla.hotel.ui.itembuilder.impl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.senla.hotel.context.ApplicationContext;
import com.senla.hotel.domain.Service;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.ConsoleReader;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.itembuilder.ServiceItemsBuilder;

public class ServiceItemsBuilderImpl implements ServiceItemsBuilder {

    private final HotelFormatter hotelFormatter = ApplicationContext.getInstance().getObject(HotelFormatter.class);
    
    private ServiceService serviceService = ApplicationContext.getInstance().getObject(ServiceService.class);
    private Integer commandNumber = 1;

    public Map<Integer, MenuItem> buildServiceItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new LinkedHashMap<>();
        result.put(commandNumber++, createMenuItem("Add service", addService, rootMenu));
        result.put(commandNumber++, createMenuItem("Change service cost", changeServiceCost, rootMenu));
        result.put(commandNumber++, createMenuItem("Find services", findServices, rootMenu));
        result.put(commandNumber++, createMenuItem("Find services costs", findServicesCosts, rootMenu));
        result.put(commandNumber++, createMenuItem("Import services", importService, rootMenu));
        result.put(commandNumber++, createMenuItem("Export service", exportService, rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    private Action addService = () -> {
        System.out.print("\nInput service name : ");
        ConsoleReader.readLine();
        String name = ConsoleReader.readLine();
        System.out.print("Input service cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();
        serviceService.create(name, cost);
    };

    private Action changeServiceCost = () -> {
        System.out.print("\nInput service id : ");
        Long id = ConsoleReader.readLong();
        System.out.print("Input new service cost : ");
        BigDecimal cost = ConsoleReader.readBigDecimal();
        serviceService.updateCost(id, cost);
    };

    private Action findServices = () -> System.out.println(hotelFormatter.formatServices(serviceService.findAll()));

    private Action findServicesCosts = () -> {
        List<Service> services = serviceService.findAll();
        System.out.println(hotelFormatter.formatServicesCosts(services));
    };

    private Action importService = () -> serviceService.importServices();

    private Action exportService = () -> {
        System.out.print("\nInput service id : ");
        Long id = ConsoleReader.readLong();
        serviceService.exportService(id);
    };
}
