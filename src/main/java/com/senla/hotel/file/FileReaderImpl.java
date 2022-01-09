package com.senla.hotel.file;

import java.io.FileInputStream;
import java.io.IOException;
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

import com.senla.hotel.annotation.Singleton;
import com.senla.hotel.exception.FileException;

@Singleton
public class FileReaderImpl implements FileReader {
    
    @Override
    public List<String> readResourceFileLines(String path) {
        try (Stream<String> lines = Files.lines(findResourcePath(path))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("File does not exist: " + path, e);
        }
    }
    
    @Override
    public Properties readProperties(String path) {
        Properties properties= new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(findResourcePath(path).toString())) {
            
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new FileException("File does not exist: " + path, e);
        }
    }
    
    private Path findResourcePath(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if(resource == null) {
            throw new FileException("Failed to get URL resource: " + path);
        }
        return Paths.get(convertURLResourceToURI(resource));
    }
    
    private URI convertURLResourceToURI(URL resource) {
        try {
            return resource.toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Failed to convert URL resource to URI", e);
        }
    }
}
