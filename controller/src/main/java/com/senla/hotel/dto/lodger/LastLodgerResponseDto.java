package com.senla.hotel.dto.lodger;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LastLodgerResponseDto {

    private Long lodgerId;
    private LocalDate date;
}
