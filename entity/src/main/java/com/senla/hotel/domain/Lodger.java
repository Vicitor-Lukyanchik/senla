package com.senla.hotel.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lodgers")
public class Lodger {

    @Id
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="phone_number")
    private String phoneNumber;

    public Lodger() {
    }

    public Lodger(String firstName, String secondName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Lodger(Long id, String firstName, String secondName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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