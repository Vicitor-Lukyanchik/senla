package com.senla.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="rooms")
@SequenceGenerator(
        name = "reservations-gen",
        sequenceName = "reservations_id_seq",
        initialValue = 1, allocationSize = 1)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservations-gen")
    private Long id;
    @Column(name = "number")
    private int number;
    @Column(name="cost")
    private BigDecimal cost;
    @Column(name="capacity")
    private int capacity;
    @Column(name="stars")
    private int stars;

    public Room(int number, BigDecimal cost, int capacity, int star) {
        this.number = number;
        this.cost = cost;
        this.capacity = capacity;
        this.stars = star;
    }
}
