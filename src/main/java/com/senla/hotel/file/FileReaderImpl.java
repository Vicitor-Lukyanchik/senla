package com.senla.hotel.file;

import com.senla.hotel.annotation.Log;
import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.FileException;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class FileReaderImpl implements FileReader {

    @Log
    private Logger log;

    @Override
    public List<String> readResourceFileLines(String path) {
        try (Stream<String> lines = Files.lines(findResourcePath(path))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }

    @Override
    public Properties readProperties(String path) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(findResourcePath(path).toString())) {

            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            String message = "File does not exist: " + path;
            log.error(message);
            throw new FileException(message, e);
        }
    }

    @Override
    public <T> T readObject(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(findResourcePath(path).toString()))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
