package com.senla.hotel.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="service_orders")
@SequenceGenerator(
        name = "lodger-gen",
        sequenceName = "lodgers_id_seq",
        initialValue = 1, allocationSize = 1)
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lodger-gen")
    private Long id;
    @Column(name="date")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lodger_id")
    private Lodger lodger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    public ServiceOrder() {
    }

    public ServiceOrder(LocalDate date, Lodger lodger, Service service) {
        this.date = date;
        this.lodger = lodger;
        this.service = service;
    }

    public ServiceOrder(Long id, LocalDate date, Lodger lodger, Service service) {
        this.id = id;
        this.date = date;
        this.lodger = lodger;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Lodger getLodger() {
        return lodger;
    }

    public void setLodger(Lodger lodger) {
        this.lodger = lodger;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service serviceId) {
        this.service = serviceId;
    }
}
