package com.senla.hotel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="service_orders")
public class ServiceOrder {

    @Id
    private Long id;
    @Column(name="date")
    private LocalDate date;
    @Column(name="lodger_id")
    private Long lodgerId;
    @Column(name="service_id")
    private Long serviceId;

    public ServiceOrder() {
    }

    public ServiceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        this.date = date;
        this.lodgerId = lodgerId;
        this.serviceId = serviceId;
    }

    public ServiceOrder(Long id, LocalDate date, Long lodgerId, Long serviceId) {
        this.id = id;
        this.date = date;
        this.lodgerId = lodgerId;
        this.serviceId = serviceId;
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

    public Long getLodgerId() {
        return lodgerId;
    }

    public void setLodgerId(Long lodgerId) {
        this.lodgerId = lodgerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
