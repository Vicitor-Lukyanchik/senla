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
@Table(name="service_orders")
@SequenceGenerator(
        name = "service_orders-gen",
        sequenceName = "service_orders_id_seq",
        initialValue = 1, allocationSize = 1)
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_orders-gen")
    private Long id;
    @Column(name="date")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lodger_id")
    private Lodger lodger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    public ServiceOrder(LocalDate date, Lodger lodger, Service service) {
        this.date = date;
        this.lodger = lodger;
        this.service = service;
    }
}
