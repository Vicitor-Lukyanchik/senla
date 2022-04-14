package com.senla.hotel.file;

import java.util.List;

public interface CsvFileWriter {
    void writeResourceFileLines(String path, List<String> lines);
}
