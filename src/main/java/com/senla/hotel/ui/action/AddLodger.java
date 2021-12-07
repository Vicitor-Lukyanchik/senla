package com.senla.hotel.ui.action;

import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class AddLodger implements Action {

    private LodgerService lodgerService;

    public AddLodger() {
        lodgerService = LodgerServiceImpl.getInstance();
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

        lodgerService.create(firstName, lastName, phone);
    }
}
