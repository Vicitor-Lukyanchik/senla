package com.senla.hotel.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
@SequenceGenerator(
        name = "lodger-gen",
        sequenceName = "lodgers_id_seq",
        initialValue = 1, allocationSize = 1)
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lodger-gen")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private BigDecimal cost;

    public Service() {
    }

    public Service(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }

    public Service(Long id, String name, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
