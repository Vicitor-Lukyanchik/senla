package com.senla.hotel.domain;

public class Worker {

    private Integer id;
    private String firstName;
    private String secondName;
    private Integer serviceId;

    public Worker() {
    }
    
    public Worker(String firstName, String secondName, Integer serviceId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.serviceId = serviceId;
    }

    public Worker(Integer id, String firstName, String secondName, Integer serviceId) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.serviceId = serviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
