package com.senla.hotel.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SaveReservationRequestDto {

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate startDate;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate endDate;

    private Long lodgerId;
    private Long roomId;
    private boolean reserved;
}
