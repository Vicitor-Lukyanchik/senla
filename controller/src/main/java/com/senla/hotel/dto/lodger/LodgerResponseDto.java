package com.senla.hotel.dto.lodger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LodgerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
