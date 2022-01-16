package com.senla.hotel.serializer;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.domain.Hotel;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.repository.ServiceRepository;

public class RepositoriesSerializer implements Serializer {

    private static final String HOTEL_PATH = "serialize/hotel.bin";

    @InjectByType
    private FileWriter fileWriter;
    @InjectByType
    private FileReader fileReader;
    @InjectByType
    private LodgerRepository lodgerRepository;
    @InjectByType
    private ReservationRepository reservationRepository;
    @InjectByType
    private RoomRepository roomRepository;
    @InjectByType
    private ServiceRepository serviceRepository;
    @InjectByType
    private ServiceOrderRepository serviceOrderRepository;
    @InjectByType
    private Hotel hotel;

    @Override
    public void deserialize() {
        hotel = fileReader.readObject(HOTEL_PATH);
        lodgerRepository.setLodgers(hotel.getLodgers());
        reservationRepository.setReservations(hotel.getReservations());
        roomRepository.setRooms(hotel.getRooms());
        serviceRepository.setServices(hotel.getServices());
        serviceOrderRepository.setServiceOrders(hotel.getServiceOrders());
    }
    
    @Override
    public void serialize() {
        hotel.setLodgers(lodgerRepository.getLodgers());
        hotel.setReservations(reservationRepository.getReservations());
        hotel.setRooms(roomRepository.getRooms());
        hotel.setServices(serviceRepository.getServices());
        hotel.setServiceOrders(serviceOrderRepository.getServiceOrders());
        fileWriter.writeObjectOnResourceFileLines(HOTEL_PATH, hotel);
    }
}
