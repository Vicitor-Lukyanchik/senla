package com.senla.hotel.parser.impl;

import java.util.ArrayList;
import java.util.List;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.parser.LodgersParser;

public class LodgersParserImpl implements LodgersParser {

    private static final String NEXT_COLUMN = ";";

    @Override
    public List<Lodger> parseLodgers(List<String> lodgers) {
        List<Lodger> result = new ArrayList<>();
        for (String lodger : lodgers) {
            result.add(parseLodger(lodger.split(NEXT_COLUMN)));
        }
        return result;
    }

    private Lodger parseLodger(String[] lodger) {
        Long id = Long.parseLong(lodger[0]);
        String firstName = lodger[1];
        String lastName = lodger[2];
        String phone = lodger[3];
        return new Lodger(id, firstName, lastName, phone);
    }

    @Override
    public List<String> parseLines(List<Lodger> lodgers) {
        List<String> result = new ArrayList<>();
        for (Lodger lodger : lodgers) {
            result.add(parseLine(lodger));
        }
        return result;
    }

    private String parseLine(Lodger lodger) {
        return lodger.getId() + NEXT_COLUMN + lodger.getFirstName() + NEXT_COLUMN + lodger.getLastName() + NEXT_COLUMN
                + lodger.getPhoneNumber();
    }
}
