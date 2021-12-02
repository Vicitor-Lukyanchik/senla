package com.senla.hotel.domain;

import java.util.LinkedList;
import java.util.List;

public class Hotel {

    private List<Room> rooms = new LinkedList<>();
    private List<Service> services = new LinkedList<>();
    private List<Lodger> lodgers = new LinkedList<>();
    private List<Reservation> reservations = new LinkedList<>();
    private List<ServiceOrder> serviceOrders = new LinkedList<>();

    public boolean addServiceOrder(ServiceOrder serviceOrder) {
        validateServiceOrder(serviceOrder);
        return serviceOrders.add(serviceOrder);
    }

    public boolean deleteServiceOrder(ServiceOrder serviceOrder) {
        validateServiceOrder(serviceOrder);
        return serviceOrders.remove(serviceOrder);
    }

    private void validateServiceOrder(ServiceOrder serviceOrders) {
        if (serviceOrders == null) {
            throw new IllegalArgumentException("ServiceOrder can not be null");
        }
    }

    public boolean addReservation(Reservation reservation) {
        validateReservation(reservation);
        return reservations.add(reservation);
    }

    public boolean deleteReservation(Reservation reservation) {
        validateReservation(reservation);
        return reservations.remove(reservation);
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }
    }

    public boolean addLodger(Lodger lodger) {
        validateLodger(lodger);
        return lodgers.add(lodger);
    }

    public boolean deleteLodger(Lodger lodger) {
        validateLodger(lodger);
        return lodgers.remove(lodger);
    }

    private void validateLodger(Lodger lodger) {
        if (lodger == null) {
            throw new IllegalArgumentException("Lodger can not be null");
        }
    }

    public boolean addRoom(Room room) {
        validateRoom(room);
        return rooms.add(room);
    }

    public boolean deleteRoom(Room room) {
        validateRoom(room);
        return rooms.remove(room);
    }

    private void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room can not be null");
        }
    }

    public void addService(Service service) {
        validateService(service);
        services.add(service);
    }

    public boolean deleteService(Service service) {
        validateService(service);
        return services.remove(service);
    }

    private void validateService(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Service can not be null");
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<Lodger> getLodgers() {
        return lodgers;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }
}
