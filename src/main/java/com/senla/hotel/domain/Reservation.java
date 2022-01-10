package com.senla.hotel.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long lodgerId;
    private Long roomId;
    private boolean isReserved = true;

    public Reservation() {
    }

    public Reservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
    }

    public Reservation(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getLodgerId() {
        return lodgerId;
    }

    public void setLodgerId(Long lodgerId) {
        this.lodgerId = lodgerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void isNotReserved() {
        isReserved = false;
    }
}
