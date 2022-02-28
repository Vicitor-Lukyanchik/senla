package com.senla.hotel.parser;

import com.senla.hotel.annotation.Log;
import com.senla.hotel.exception.ValidatorException;
import org.apache.logging.log4j.Logger;

public class Validator {

    private static final String REGEX_PHONE = "\\d{7}";
    private static final String REGEX_PRICE = "\\d+[.]\\d{2}";
    private static final String REGEX_DATE = "\\d{2}+[.]\\d{2}+[.]\\d{2}";

    @Log
    private static Logger log;

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
