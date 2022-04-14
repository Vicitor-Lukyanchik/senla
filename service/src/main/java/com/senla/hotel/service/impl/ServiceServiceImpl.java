package com.senla.hotel.service.impl;

import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;
import com.senla.hotel.exception.FileException;
import com.senla.hotel.file.CsvFileReader;
import com.senla.hotel.file.CsvFileWriter;
import com.senla.hotel.parser.CsvParser;
import com.senla.hotel.service.ServiceService;
import com.senla.hotel.service.connection.hibernate.HibernateUtil;
import com.senla.hotel.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private static final String SERVICES_PATH = "services.csv";
    private static final String EMPTY_LINE = "";

    @Autowired
    private CsvFileReader fileReader;
    @Autowired
    private CsvParser csvParser;
    @Autowired
    private CsvFileWriter fileWriter;
    @Autowired
    private ServiceDao serviceDao;
    @Autowired
    private HibernateUtil hibernateUtil;
    private List<Service> csvServices = new ArrayList<>();

    @Override
    public void create(Service service) {
        validateService(service.getName());
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        serviceDao.setType(Service.class);
        try {
            serviceDao.create(service, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
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
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        serviceDao.setType(Service.class);
        try {
            serviceDao.create(importService, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
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
            String message = "Service name can not be empty";
            log.error(message);
            throw new ServiceException(message);
        }
    }

    @Override
    public void updateCost(Long id, BigDecimal cost) {
        Service service = findById(id);
        service.setCost(cost);
        update(service);
    }

    private void update(Service service) {
        Session session = hibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        serviceDao.setType(Service.class);
        try {
            serviceDao.update(service, session);
            transaction.commit();
        } catch (DAOException e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public Service findById(Long id) {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        serviceDao.setType(Service.class);
        Service service = serviceDao.findById(session, id);
        if (service == null) {
            throw new ServiceException("There is not service with this id " + id);
        }
        return service;
    }

    @Override
    public List<Service> findAll() {
        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        serviceDao.setType(Service.class);
        List<Service> result = serviceDao.findAll(session);
        session.close();
        return result;
    }
}
