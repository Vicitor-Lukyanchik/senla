package com.senla.hotel.filerepository.impl;

import java.util.List;

import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.filerepository.ServiceFileRepository;
import com.senla.hotel.parser.ServicesParser;
import com.senla.hotel.parser.impl.ServicesParserImpl;
import com.senla.hotel.reader.FileReader;
import com.senla.hotel.reader.FileReaderImpl;
import com.senla.hotel.writer.FileWriter;
import com.senla.hotel.writer.FileWriterImpl;

public class ServiceFileRepositoryImpl implements ServiceFileRepository {

    private static final String PATH = "services.csv";

    private static ServiceFileRepository instance;

    private final FileReader fileReader = new FileReaderImpl();
    private final ServicesParser serviceParser = new ServicesParserImpl();
    private final FileWriter fileWriter = new FileWriterImpl();

    private List<Service> services = getServicesFromFile();

    private List<Service> getServicesFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return serviceParser.parseServices(lines);
    }

    public static ServiceFileRepository getInstance() {
        if (instance == null) {
            instance = new ServiceFileRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Service findById(Long id) {
        Service service = services.stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
        if (service != null) {
            return service;
        }
        throw new FileRepositoryException("There is not service with id = " + id);
    }

    @Override
    public void export(Service service) {
        try {
            Service result = findById(service.getId());
            result.setName(service.getName());
            result.setCost(service.getCost());
        } catch (FileRepositoryException ex) {
            services.add(service);
        }
        List<String> lines = serviceParser.parseLines(services);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
}
