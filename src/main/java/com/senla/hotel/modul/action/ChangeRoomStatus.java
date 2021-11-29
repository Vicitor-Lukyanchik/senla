package com.senla.hotel.modul.action;

import com.senla.hotel.domain.Hotel;
import com.senla.hotel.reader.ConsoleReader;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.impl.RoomServiceImpl;

public class ChangeRoomStatus implements Action {

    private RoomService roomService;

    public ChangeRoomStatus(Hotel hotel) {
        roomService = new RoomServiceImpl(hotel);
    }

    @Override
    public void execute() {
        System.out.print("\nInput room id : ");
        Integer id = ConsoleReader.readNumber();

        roomService.updateStatus(id);
    }
}
