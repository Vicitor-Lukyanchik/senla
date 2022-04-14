package com.senla.hotel.dto.lodger;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReservationCostsResponseDto {

    private Long roomId;
    private BigDecimal cost;
}
