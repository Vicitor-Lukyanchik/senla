package com.senla.hotel.reader;

import java.util.List;

public interface FileReader {    
    List<String> readResourceFileLines(String path);
}
