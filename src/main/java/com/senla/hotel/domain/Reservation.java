package com.senla.hotel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    private Long id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "lodger_id")
    private Long lodgerId;
    @Column(name = "room_id")
    private Long roomId;
    @Column(name = "reserved")
    private boolean reserved = true;

    public Reservation() {
    }

    public Reservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId, Boolean reserved) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
        this.reserved = reserved;
    }

    public Reservation(Long id, LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId, Boolean reserved) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodgerId = lodgerId;
        this.roomId = roomId;
        this.reserved = reserved;
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
        return reserved;
    }

    public void setReserved(boolean isReserved) {
        this.reserved = isReserved;
    }
}
