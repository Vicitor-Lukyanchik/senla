package com.senla.hotel.file;

import java.util.List;

public interface CsvFileReader {
    List<String> readResourceFileLines(String path);
}
