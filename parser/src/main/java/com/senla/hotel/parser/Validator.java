package com.senla.hotel.parser;

import com.senla.hotel.exception.ValidatorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
@Log4j2
public class Validator {

    private static final String REGEX_PHONE = "\\d{7}";
    private static final String REGEX_PRICE = "\\d+[.]\\d{2}";
    private static final String REGEX_DATE = "\\d{2}+[.]\\d{2}+[.]\\d{4}";

    private Validator() {
    }

    public static void validatePrice(String price) {
        if (!price.matches(REGEX_PRICE)) {
            String message = "Price should be this format : D.CC";
            log.error(message);
            throw new ValidatorException(message);
        }
    }

    public static void validateDate(String date) {
        if (!date.matches(REGEX_DATE)) {
            String message = "Date should be this format : DD.MM.YYYY";
            log.error(message);
            throw new ValidatorException(message);
        }
    }

    public static void validatePhone(String phone) {
        if (!phone.matches(REGEX_PHONE)) {
            String message = "Phone number should be this format : DDDDDDD";
            log.error(message);
            throw new ValidatorException(message);
        }
    }
}
