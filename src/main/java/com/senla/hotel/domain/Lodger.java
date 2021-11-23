package com.senla.hotel.domain;

public class Lodger {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Lodger() {
    }
    
    public Lodger(String firstName, String secondName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Lodger(Integer id, String firstName, String secondName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
        this.phoneNumber = phoneNumber;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }
    
    public String getFirstLastName() {
        return firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
