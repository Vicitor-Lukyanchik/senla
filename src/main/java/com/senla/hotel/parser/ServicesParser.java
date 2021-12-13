package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.Service;

public interface ServicesParser {
    List<Service> parseServices(List<String> services);
    
    List<String> parseLines(List<Service> services);
}
