package com.senla.hotel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name="rooms")
public class Room {

    @Id
    private Long id;
    @Column(name = "number")
    private int number;
    @Column(name="cost")
    private BigDecimal cost;
    @Column(name="capacity")
    private int capacity;
    @Column(name="stars")
    private int stars;

    public Room() {
    }

    public Room(int number, BigDecimal cost, int capacity, int star) {
        this.number = number;
        this.cost = cost;
        this.capacity = capacity;
        this.stars = star;
    }

    public Room(Long id, int number, BigDecimal cost, int capacity, int star) {
        this.id = id;
        this.number = number;
        this.cost = cost;
        this.capacity = capacity;
        this.stars = star;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int star) {
        this.stars = star;
    }
}
