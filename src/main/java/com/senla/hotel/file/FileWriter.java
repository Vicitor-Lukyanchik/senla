package com.senla.hotel.file;

import java.util.List;

public interface FileWriter {
    void writeResourceFileLines(String path, List<String> lines);

    <T> void writeObjectOnResourceFileLines(String path, List<T> object);
}
