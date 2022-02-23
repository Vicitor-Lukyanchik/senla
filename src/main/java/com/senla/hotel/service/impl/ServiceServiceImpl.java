package com.senla.hotel.service.impl;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.dao.connection.Transaction;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.exception.ServiceException;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.ServiceService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ServiceServiceImpl implements ServiceService {

    private static final String SERVICES_PATH = "csv/services.csv";
    private static final String EMPTY_LINE = "";

    @InjectByType
    private FileReader fileReader;
    @InjectByType
    private CsvParser csvParser;
    @InjectByType
    private FileWriter fileWriter;
    @InjectByType
    private ServiceDao serviceDao;
    @InjectByType
    private Transaction transaction;
    private List<Service> csvServices = new ArrayList<>();

    @Override
    public void create(String name, BigDecimal cost) {
        validateService(name);
        try {
            transaction.begin();
            serviceDao.create(new Service(name, cost), transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    @Override
    public void importServices() {
        csvServices = getServicesFromFile();
        for (Service importService : csvServices) {
            validateService(importService.getName());
            try {
                findById(importService.getId());
                update(importService);
            } catch (ServiceException ex) {
                createWithId(importService);
            }
        }
    }

    private void createWithId(Service importService) {
        try {
            transaction.begin();
            serviceDao.createWithId(importService, transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    private List<Service> getServicesFromFile() {
        List<String> lines = fileReader.readResourceFileLines(SERVICES_PATH);
        return csvParser.parseServices(lines);
    }

    @Override
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

    private void validateService(String name) {
        if (name.equals(EMPTY_LINE)) {
            throw new ServiceException("Service name can not be empty");
        }
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Service service = findById(id);
        service.setCost(cost);
        update(service);
    }

    private void update(Service service) {
        try {
            transaction.begin();
            serviceDao.update(service, transaction.getConnection());
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    @Override
    public Service findById(Long id) {
        for (Service service : findAll()) {
            if (service.getId().equals(id)) {
                return service;
            }
        }
        throw new ServiceException("There is not service with this id " + id);
    }

    @Override
    public List<Service> findAll() {
        transaction.begin();
        List<Service> result = serviceDao.findAll(transaction.getConnection());
        transaction.end();
        return result;
    }
}
