package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class AddLodger implements Action {

    private LodgerService lodgerService;

    public AddLodger(Hotel hotel) {
        lodgerService = new LodgerServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput lodger first-name : ");
        ConsoleReader.readLine();
        String firstName = ConsoleReader.readLine();
        System.out.print("Input lodger last-name : ");
        String lastName = ConsoleReader.readLine();
        System.out.print("Input lodger phone number : ");
        String phone = ConsoleReader.readLine();
        lodgerService.create(createLodger(firstName, lastName, phone));
    }

    private Lodger createLodger(String firstName, String lastName, String phone) {
        return new Lodger(firstName, lastName, phone);
    }
}
