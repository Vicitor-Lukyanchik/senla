package com.senla.hotel.file;

import com.senla.hotel.exception.FileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

@Component
@Log4j2
public class CsvFileWriterImpl implements CsvFileWriter {

    @Override
    public void writeResourceFileLines(String path, List<String> lines) {
        try (FileWriter fileWriter = new FileWriter(new ClassPathResource(path).getPath());
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.print(lines);
        } catch (Exception e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }
}
