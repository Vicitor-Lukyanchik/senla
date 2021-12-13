package com.senla.hotel.writer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.senla.hotel.exception.FileException;

public class FileWriterImpl implements FileWriter {

    @Override
    public void writeResourceFileLines(String path, List<String> lines) {
        try {
            Files.write(findResourcePath(path), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileException("File does not exist: " + path, e);
        }
    }

    private Path findResourcePath(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) {
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
