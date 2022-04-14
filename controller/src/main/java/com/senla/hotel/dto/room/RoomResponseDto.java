package com.senla.hotel.dto.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponseDto {
    private Long id;
    private int number;
    private BigDecimal cost;
    private int capacity;
    private int stars;
}
