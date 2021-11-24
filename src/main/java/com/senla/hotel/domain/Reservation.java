package com.senla.hotel.domain;

import java.time.LocalDate;

public class Reservation {

    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer lodgerId;
    private Integer roomId;

    public Reservation() {
    }
    
    public Reservation(LocalDate startDate, LocalDate endDate, Integer lodgerId, Integer roomId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
    }

    public Reservation(Integer id, LocalDate startDate, LocalDate endDate, Integer lodgerId, Integer roomId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getLodgerId() {
        return lodgerId;
    }

    public void setLodgerId(Integer lodgerId) {
        this.lodgerId = lodgerId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
