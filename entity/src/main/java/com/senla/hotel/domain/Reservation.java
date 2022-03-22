package com.senla.hotel.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@SequenceGenerator(
        name = "lodger-gen",
        sequenceName = "lodgers_id_seq",
        initialValue = 1, allocationSize = 1)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lodger-gen")
    private Long id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lodger_id")
    private Lodger lodger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    @Column(name = "reserved")
    private boolean reserved = true;

    public Reservation() {
    }

    public Reservation(LocalDate startDate, LocalDate endDate, Lodger lodger, Room room, Boolean reserved) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodger = lodger;
        this.room = room;
        this.reserved = reserved;
    }

    public Reservation(Long id, LocalDate startDate, LocalDate endDate, Lodger lodger, Room room, Boolean reserved) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodger = lodger;
        this.room = room;
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

    public Lodger getLodger() {
        return lodger;
    }

    public void setLodger(Lodger lodger) {
        this.lodger = lodger;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean isReserved) {
        this.reserved = isReserved;
    }
}
