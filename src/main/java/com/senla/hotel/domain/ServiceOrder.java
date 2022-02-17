package com.senla.hotel.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ServiceOrder {

    private Long id;
    private LocalDate date;
    private Long lodgerId;
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
