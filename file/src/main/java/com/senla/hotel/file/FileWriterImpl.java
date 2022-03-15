package com.senla.hotel.file;

import com.senla.hotel.exception.FileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@Scope("singleton")
@Log4j2
public class FileWriterImpl implements FileWriter {

    @Override
    public void writeResourceFileLines(String path, List<String> lines) {
        try {
            Files.write(findResourcePath(path), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }

    @Override
    public <T> void writeObjectOnResourceFileLines(String path, T object) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(findResourcePath(path).toString()))) {
            oos.writeObject(object);
        } catch (IOException e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }

    private Path findResourcePath(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
            String message = "Failed to get URL resource: " + path;
            log.error(message);
            throw new FileException(message);
        }
        return Paths.get(convertURLResourceToURI(resource));
    }

    private URI convertURLResourceToURI(URL resource) {
        try {
            return resource.toURI();
        } catch (URISyntaxException e) {
            String message = "Failed to convert URL resource to URI";
            log.error(message);
            throw new FileException(message, e);
        }
    }
}
