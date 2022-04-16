package com.senla.hotel.service.impl;

import com.senla.hotel.entity.Service;
import com.senla.hotel.file.CsvFileReader;
import com.senla.hotel.file.CsvFileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.repository.ServiceRepository;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private static final String SERVICES_PATH = "services.csv";
    private static final String EMPTY_LINE = "";

    private final CsvFileReader fileReader;
    private final CsvParser csvParser;
    private final CsvFileWriter fileWriter;
    private final ServiceRepository serviceRepository;

    private List<Service> csvServices = new ArrayList<>();

    @Override
    @Transactional
    public void importServices() {
        csvServices = getServicesFromFile();
        for (Service importService : csvServices) {
            validateService(importService.getName());
            try {
                findById(importService.getId());
                update(importService);
            } catch (ServiceException ex) {
                create(importService);
            }
        }
    }

    private List<Service> getServicesFromFile() {
        List<String> lines = fileReader.readResourceFileLines(SERVICES_PATH);
        return csvParser.parseServices(lines);
    }

    @Override
    @Transactional
    public void create(Service service) {
        validateService(service.getName());
        serviceRepository.create(service);
    }

    private void validateService(String name) {
        if (name.equals(EMPTY_LINE)) {
            String message = "Service name can not be empty";
            log.error(message);
            throw new ServiceException(message);
        }
    }

    @Override
    @Transactional
    public void exportService(Long id) {
        Service service = findById(id);
        Service exportService = csvServices.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (exportService == null) {
            csvServices.add(service);
        } else {
            exportService.setName(service.getName());
            exportService.setCost(service.getCost());
        }
        List<String> lines = csvParser.parseServicesToLines(csvServices);
        fileWriter.writeResourceFileLines(SERVICES_PATH, lines);
    }

    @Override
    @Transactional
    public void updateCost(Long id, BigDecimal cost) {
        Service service = findById(id);
        service.setCost(cost);
        update(service);
    }

    @Transactional
    public void update(Service service) {
        serviceRepository.update(service);
    }

    @Override
    @Transactional
    public Service findById(Long id) {
        Service service = serviceRepository.findById(Service.class, id);
        if (service == null) {
            throw new ServiceException("There is not service with this id " + id);
        }
        return service;
    }

    @Override
    @Transactional
    public List<Service> findAll() {
        return serviceRepository.findAll(Service.class);
    }
}
