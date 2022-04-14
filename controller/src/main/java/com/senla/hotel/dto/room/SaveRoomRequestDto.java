package com.senla.hotel.dto.room;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class SaveRoomRequestDto {

    @NotBlank(message = "incorrect number")
    @NotEmpty(message = "number cannot be empty")
    @DecimalMin(value = "1")
    private int number;

    @NotBlank(message = "incorrect cost")
    @NotEmpty(message = "cost cannot be empty")
    @DecimalMin(value = "0.0")
    private BigDecimal cost;

    @NotBlank(message = "incorrect capacity")
    @NotEmpty(message = "capacity cannot be empty")
    @DecimalMin(value = "1")
    private int capacity;

    @NotBlank(message = "incorrect stars")
    @NotEmpty(message = "stars cannot be empty")
    @DecimalMin(value = "1")
    @DecimalMax(value = "5")
    private int stars;
}
