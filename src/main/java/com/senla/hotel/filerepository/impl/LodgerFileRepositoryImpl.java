package com.senla.hotel.filerepository.impl;

import java.util.List;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.exception.FileRepositoryException;
import com.senla.hotel.filerepository.LodgerFileRepository;
import com.senla.hotel.parser.LodgersParser;
import com.senla.hotel.parser.impl.LodgersParserImpl;
import com.senla.hotel.reader.FileReader;
import com.senla.hotel.reader.FileReaderImpl;
import com.senla.hotel.writer.FileWriter;
import com.senla.hotel.writer.FileWriterImpl;

public class LodgerFileRepositoryImpl implements LodgerFileRepository {

    private static final String PATH = "lodgers.csv";

    private static LodgerFileRepository instance;

    private final FileReader fileReader = new FileReaderImpl();
    private final LodgersParser lodgerParser = new LodgersParserImpl();
    private final FileWriter fileWriter = new FileWriterImpl();

    private List<Lodger> lodgers = getLodgersFromFile();

    private List<Lodger> getLodgersFromFile() {
        List<String> lines = fileReader.readResourceFileLines(PATH);
        return lodgerParser.parseLodgers(lines);
    }

    public static LodgerFileRepository getInstance() {
        if (instance == null) {
            instance = new LodgerFileRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Lodger findById(Long id) {
        Lodger lodger = lodgers.stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
        if (lodger != null) {
            return lodger;
        }
        throw new FileRepositoryException("There is not lodger with id = " + id);
    }

    @Override
    public void export(Lodger lodger) {
        try {
            Lodger result = findById(lodger.getId());
            result.setFirstName(lodger.getFirstName());
            result.setLastName(lodger.getLastName());
            result.setPhoneNumber(lodger.getPhoneNumber());
        } catch (FileRepositoryException ex) {
            lodgers.add(lodger);
        }
        List<String> lines = lodgerParser.parseLines(lodgers);
        fileWriter.writeResourceFileLines(PATH, lines);
    }
}
