package com.senla.hotel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
@SequenceGenerator(
        name = "services-gen",
        sequenceName = "services_id_seq",
        initialValue = 1, allocationSize = 1)
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "services-gen")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private BigDecimal cost;

    public Service(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }
}
