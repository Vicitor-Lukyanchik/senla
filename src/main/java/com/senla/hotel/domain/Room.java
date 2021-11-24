package com.senla.hotel.domain;

import java.math.BigDecimal;

public class Room {

    private Integer id;
    private int number;
    private BigDecimal cost;
    private int capacity;
    private int star;
    private boolean isSettled = false;
    private boolean isRepaired = false;

    public Room() {
    }
    
    public Room(int number, BigDecimal cost, int capacity, int star, boolean isSettled, boolean isRepaired) {
        this.number = number;
        this.cost = cost;
        this.capacity = capacity;
        this.star = star;
        this.isSettled = isSettled;
        this.isRepaired = isRepaired;
    }

    public Room(Integer id, int number, BigDecimal cost, int capacity, int star, boolean isSettled, boolean isRepaired) {
        this.id = id;
        this.number = number;
        this.cost = cost;
        this.capacity = capacity;
        this.star = star;
        this.isSettled = isSettled;
        this.isRepaired = isRepaired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setSettled(boolean isSettled) {
        this.isSettled = isSettled;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void settle() {
        isSettled = true;
    }
    
    public void evict() {
        isSettled = false;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void setRepaired(boolean isRepaired) {
        this.isRepaired = isRepaired;
    }
}
