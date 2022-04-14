package com.senla.hotel.dto.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomCostsResponseDto {

    private Long id;
    private BigDecimal cost;
}
