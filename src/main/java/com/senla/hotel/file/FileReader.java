package com.senla.hotel.file;

import java.util.List;
import java.util.Properties;

public interface FileReader {    
    List<String> readResourceFileLines(String path);
    
    Properties readProperties(String path);
}
