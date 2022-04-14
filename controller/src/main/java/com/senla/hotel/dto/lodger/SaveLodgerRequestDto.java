package com.senla.hotel.dto.lodger;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SaveLodgerRequestDto {

    @NotBlank(message = "incorrect firstname")
    @NotEmpty(message = "firstname cannot be empty")
    @Size(min = 2, max = 25, message
            = "firstname length should be between 2 and 25")
    private String firstName;

    @NotBlank(message = "incorrect lastname")
    @NotEmpty(message = "lastname cannot be empty")
    @Size(min = 2, max = 25, message
            = "lastname length should be between 2 and 25")
    private String lastName;

    @NotBlank(message = "incorrect phone number")
    @NotEmpty(message = "phone number cannot be empty")
    @Size(min = 7, max = 7, message
            = "phone number length should be 7")
    private String phoneNumber;
}
