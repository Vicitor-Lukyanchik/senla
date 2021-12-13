package com.senla.hotel.parser;

import java.util.List;

import com.senla.hotel.domain.Lodger;

public interface LodgersParser {
    List<Lodger> parseLodgers(List<String> lodgers);

    List<String> parseLines(List<Lodger> lodgers);
}
