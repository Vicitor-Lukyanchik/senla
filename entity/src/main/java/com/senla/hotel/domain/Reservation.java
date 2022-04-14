package com.senla.hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
@SequenceGenerator(
        name = "reservations-gen",
        sequenceName = "reservations_id_seq",
        initialValue = 1, allocationSize = 1)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations-gen")
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

    public Reservation(LocalDate startDate, LocalDate endDate, Lodger lodger, Room room, Boolean reserved) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lodger = lodger;
        this.room = room;
        this.reserved = reserved;
    }
}
