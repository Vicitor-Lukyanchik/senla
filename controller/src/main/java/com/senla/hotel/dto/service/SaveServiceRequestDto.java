package com.senla.hotel.dto.service;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class SaveServiceRequestDto {

    @NotBlank(message = "incorrect name")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "incorrect cost")
    @NotEmpty(message = "cost cannot be empty")
    @DecimalMin(value = "0.0")
    private BigDecimal cost;
}
