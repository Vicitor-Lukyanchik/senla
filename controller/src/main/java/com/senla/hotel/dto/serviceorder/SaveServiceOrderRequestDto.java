package com.senla.hotel.dto.serviceorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SaveServiceOrderRequestDto {

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate date;

    private Long lodgerId;
    private Long serviceId;
}
