package com.senla.hotel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.infrastucture.ApplicationContext;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.ServiceRepository;
import com.senla.hotel.service.ServiceService;

@Singleton
public class ServiceServiceImpl implements ServiceService {

    private static final String PATH = "services.csv";

    private final FileReader fileReader = ApplicationContext.getInstance().getObject(FileReader.class);;
    private final CsvParser csvParser = ApplicationContext.getInstance().getObject(CsvParser.class);;
    private final FileWriter fileWriter = ApplicationContext.getInstance().getObject(FileWriter.class);;

    private ServiceRepository serviceRepository = ApplicationContext.getInstance().getObject(ServiceRepository.class);;
    private Long id = 0l;
    private List<Service> importServices = new ArrayList<>();

    @Override
    public void create(String name, BigDecimal cost) {
        validateService(name);
        serviceRepository.addService(new Service(generateId(), name, cost));
        id++;
    }

    private Long generateId() {
        try {
            while (true) {
                id++;
                findById(id);
            }
        } catch (ServiceException ex) {
            return id;
        }
    }

    @Override
    public void importServices() {
        importServices = getServicesFromFile();
        for (Service importService : importServices) {
            validateService(importService.getName());
            try {
                Service service = findById(id);
                service.setName(importService.getName());
                service.setCost(importService.getCost());
            } catch (ServiceException ex) {
                serviceRepository.addService(
                        new Service(importService.getId(), importService.getName(), importService.getCost()));
            }
        }
    }

    private List<Service> getServicesFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return csvParser.parseServices(lines);
    }

    @Override
    public void exportService(Long id) {
            Service service = findById(id);
            Service importService = importServices.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
            if(importService == null) {
                importServices.add(service);
            } else {
                importService.setName(service.getName());
                importService.setCost(service.getCost());
            }
        List<String> lines = csvParser.parseServicesToLines(importServices);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
    
    private void validateService(String name) {
        if (name.equals("")) {
            throw new ServiceException("Service neme can not be empty");
        }
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Service service = findById(id);
        service.setCost(cost);
    }

    @Override
    public Service findById(Long id) {
        for (Service service : serviceRepository.getServices()) {
            if (service.getId().equals(id)) {
                return service;
            }
        }
        throw new ServiceException("There is not service with this id");
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.getServices();
    }
}
