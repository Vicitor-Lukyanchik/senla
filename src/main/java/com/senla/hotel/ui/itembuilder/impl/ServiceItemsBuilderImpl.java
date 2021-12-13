package com.senla.hotel.ui.itembuilder.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.filerepository.ServiceFileRepository;
import com.senla.hotel.filerepository.impl.ServiceFileRepositoryImpl;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.impl.ServiceServiceImpl;
import com.senla.hotel.ui.Action;
import com.senla.hotel.ui.Menu;
import com.senla.hotel.ui.MenuItem;
import com.senla.hotel.ui.formatter.HotelFormatter;
import com.senla.hotel.ui.formatter.HotelFormatterImpl;
import com.senla.hotel.ui.itembuilder.ServiceItemsBuilder;

public class ServiceItemsBuilderImpl implements ServiceItemsBuilder {

    private static ServiceItemsBuilder instance;

    private final HotelFormatter hotelFormatter = new HotelFormatterImpl();

    private Integer commandNumber = 1;
    private ServiceService serviceService;
    private ServiceFileRepository serviceFile;

    public ServiceItemsBuilderImpl() {
        serviceService = ServiceServiceImpl.getInstance();
        serviceFile = ServiceFileRepositoryImpl.getInstance();
    }

    public static ServiceItemsBuilder getInstance() {
        if (instance == null) {
            instance = new ServiceItemsBuilderImpl();
        }
        return instance;
    }

    public Map<Integer, MenuItem> buildServiceItems(Menu rootMenu) {
        Map<Integer, MenuItem> result = new HashMap<>();
        result.put(commandNumber++, createMenuItem("Add service", addService(), rootMenu));
        result.put(commandNumber++, createMenuItem("Change service cost", changeServiceCost(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find services", findServices(), rootMenu));
        result.put(commandNumber++, createMenuItem("Find services costs", findServicesCosts(), rootMenu));
        result.put(commandNumber++, createMenuItem("Import services", importService(), rootMenu));
        result.put(commandNumber++, createMenuItem("Export services", exportService(), rootMenu));
        return result;
    }

    private MenuItem createMenuItem(String title, Action action, Menu nextMenu) {
        return new MenuItem(title, action, nextMenu);
    }

    private Action addService() {
        return () -> {
            System.out.print("\nInput service name : ");
            ConsoleReader.readLine();
            String name = ConsoleReader.readLine();
            System.out.print("Input service cost : ");
            BigDecimal cost = ConsoleReader.readBigDecimal();
            serviceService.create(name, cost);
        };
    }

    private Action changeServiceCost() {
        return () -> {
            List<Service> services = serviceService.findAll();
            System.out.println(hotelFormatter.formatServicesCosts(services));
        };
    }

    private Action findServices() {
        return () -> System.out.println(hotelFormatter.formatServices(serviceService.findAll()));

    }

    private Action findServicesCosts() {
        return () -> {
            List<Service> services = serviceService.findAll();
            System.out.println(hotelFormatter.formatServicesCosts(services));
        };
    }
    
    private Action importService() {
        return () -> {
            System.out.print("\nInput room id : ");
            Long id = ConsoleReader.readLong();
            Service importService = serviceFile.findById(id);
            try {
                Service service = serviceService.findById(id);
                service.setName(importService.getName());
                service.setCost(importService.getCost());
            } catch (ServiceException ex) {
                serviceService.createWithId(id, importService.getName(), importService.getCost());
            }
        };
    }
    
    private Action exportService() {
        return () -> {
            System.out.print("\nInput service id : ");
            Long id = ConsoleReader.readLong();
            Service exportService = serviceService.findById(id);
            serviceFile.export(exportService);
        };
    }
}
