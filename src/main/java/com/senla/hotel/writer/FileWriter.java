package com.senla.hotel.writer;

import java.util.List;

public interface FileWriter {    
    void writeResourceFileLines(String path, List<String> lines);
}
