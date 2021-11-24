package com.senla.hotel.domain;

import java.time.LocalDate;

public class ServiceOrder {

    private Integer id;
    private LocalDate date;
    private Integer lodgerId;
    private Integer serviceId;

    public ServiceOrder() {
    }
    
    public ServiceOrder(LocalDate date, Integer lodgerId, Integer serviceId) {
        this.date = date;
        this.lodgerId = lodgerId;
        this.serviceId = serviceId;
    }

    public ServiceOrder(Integer id, LocalDate date, Integer lodgerId, Integer serviceId) {
        this.id = id;
        this.date = date;
        this.lodgerId = lodgerId;
        this.serviceId = serviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getLodgerId() {
        return lodgerId;
    }

    public void setLodgerId(Integer lodgerId) {
        this.lodgerId = lodgerId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
