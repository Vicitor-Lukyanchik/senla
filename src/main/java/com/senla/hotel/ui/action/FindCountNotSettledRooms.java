package com.senla.hotel.ui.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.impl.LodgerServiceImpl;

public class FindCountNotSettledRooms implements Action {

    private static final LocalDate DATE = LocalDate.parse("02.10.2021", DateTimeFormatter.ofPattern("d.MM.yyyy"));

    private LodgerService lodgerService;

    public FindCountNotSettledRooms() {
        lodgerService = LodgerServiceImpl.getInstance();
    }

    @Override
    public void execute() {
        System.out.println("\nCount not settled rooms : " + lodgerService.findAllNotSettledRoomOnDate(DATE).size());
    }
}
