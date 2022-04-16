package com.senla.hotel.file;

import com.senla.hotel.exception.FileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class CsvFileReaderImpl implements CsvFileReader {

    private static final String SLASH = "/";

    @Override
    public List<String> readResourceFileLines(String path) {
        try (InputStream inputStream = getClass().getResourceAsStream(SLASH + path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }
}
