package com.senla.hotel.filerepository.impl;

import java.util.List;

import com.senla.hotel.domain.ServiceOrder;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.filerepository.ServiceOrderFileRepository;
import com.senla.hotel.parser.ServiceOrdersParser;
import com.senla.hotel.parser.impl.ServiceOrdersParserImpl;
import com.senla.hotel.reader.FileReader;
import com.senla.hotel.reader.FileReaderImpl;
import com.senla.hotel.writer.FileWriter;
import com.senla.hotel.writer.FileWriterImpl;

public class ServiceOrderFileRepositoryImpl implements ServiceOrderFileRepository {

    private static final String PATH = "reservations.csv";

    private static ServiceOrderFileRepository instance;

    private final FileReader fileReader = new FileReaderImpl();
    private final ServiceOrdersParser serviceOrdersParser = new ServiceOrdersParserImpl();
    private final FileWriter fileWriter = new FileWriterImpl();

    private List<ServiceOrder> serviceOrders = getServiceOrdersFromFile();

    private List<ServiceOrder> getServiceOrdersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return serviceOrdersParser.parseServiceOrders(lines);
    }

    public static ServiceOrderFileRepository getInstance() {
        if (instance == null) {
            instance = new ServiceOrderFileRepositoryImpl();
        }
        return instance;
    }

    @Override
    public ServiceOrder findById(Long id) {
        ServiceOrder serviceOrder = serviceOrders.stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
        if (serviceOrder != null) {
            return serviceOrder;
        }
        throw new FileRepositoryException("There is not serviceOrder with id = " + id);
    }

    @Override
    public void export(ServiceOrder serviceOrder) {
        try {
            ServiceOrder result = findById(serviceOrder.getId());
            result.setDate(serviceOrder.getDate());
            result.setLodgerId(serviceOrder.getLodgerId());
            result.setServiceId(serviceOrder.getServiceId());
        } catch (FileRepositoryException ex) {
            serviceOrders.add(serviceOrder);
        }
        List<String> lines = serviceOrdersParser.parseLines(serviceOrders);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
}
