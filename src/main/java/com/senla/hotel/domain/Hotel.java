package com.senla.hotel.domain;

import java.io.Serializable;
import java.util.List;

import com.senla.hotel.annotation.Singleton;

@Singleton
public class Hotel implements Serializable {

    private List<Lodger> lodgers;
    private List<Room> rooms;
    private List<Service> services;
    private List<Reservation> reservations;
    private List<ServiceOrder> serviceOrders;
    
    public List<Lodger> getLodgers() {
        return lodgers;
    }
    
    public void setLodgers(List<Lodger> lodgers) {
        this.lodgers = lodgers;
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public List<Service> getServices() {
        return services;
    }
    
    public void setServices(List<Service> services) {
        this.services = services;
    }
   
    public List<Reservation> getReservations() {
        return reservations;
    }
 
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
 
    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }
 
    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
}
