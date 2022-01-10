package com.senla.hotel.serializer;

import com.senla.hotel.annotation.InjectByType;
import com.senla.hotel.file.FileReader;
import com.senla.hotel.file.FileWriter;
import com.senla.hotel.repository.LodgerRepository;
import com.senla.hotel.repository.ReservationRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.ServiceOrderRepository;
import com.senla.hotel.repository.ServiceRepository;

public class RepositoriesSerializer implements Serializer {

    private static final String SERIALISE = "serialize/";
    private static final String LODGERS_PATH = SERIALISE + "lodgers.bin";
    private static final String ROOMS_PATH = SERIALISE + "rooms.bin";
    private static final String RESERVATIONS_PATH = SERIALISE + "reservations.bin";
    private static final String SERVICES_PATH = SERIALISE + "services.bin";
    private static final String SERVICE_ORDERS_PATH = SERIALISE + "service_orders.bin";

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

    @Override
    public void serialize() {
        fileWriter.writeObjectOnResourceFileLines(LODGERS_PATH, lodgerRepository.getLodgers());
        fileWriter.writeObjectOnResourceFileLines(ROOMS_PATH, roomRepository.getRooms());
        fileWriter.writeObjectOnResourceFileLines(RESERVATIONS_PATH, reservationRepository.getReservations());
        fileWriter.writeObjectOnResourceFileLines(SERVICES_PATH, serviceRepository.getServices());
        fileWriter.writeObjectOnResourceFileLines(SERVICE_ORDERS_PATH, serviceOrderRepository.getServiceOrders());
    }

    @Override
    public void deserialize() {
        lodgerRepository.setLodgers(fileReader.readObject(LODGERS_PATH));
        roomRepository.setRooms(fileReader.readObject(ROOMS_PATH));
        reservationRepository.setReservations(fileReader.readObject(RESERVATIONS_PATH));
        serviceRepository.setServices(fileReader.readObject(SERVICES_PATH));
        serviceOrderRepository.setServiceOrders(fileReader.readObject(SERVICE_ORDERS_PATH));
    }
}
