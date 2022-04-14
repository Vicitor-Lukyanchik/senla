package com.senla.hotel.dto.service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceResponseDto {
    private Long id;
    private String name;
    private BigDecimal cost;
}
