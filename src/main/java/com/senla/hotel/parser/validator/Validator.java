package com.senla.hotel.parser.validator;

import com.senla.hotel.exception.ValidatorException;

public class Validator {

    private static final String REGEX_PHONE = "\\d{7}";
    private static final String REGEX_PRICE = "\\d+[.]\\d{2}";
    private static final String REGEX_DATE = "\\d{2}+[.]\\d{2}+[.]\\d{2}";

    private Validator() {
    }

    public static void validatePrice(String price) {
        if (!price.matches(REGEX_PRICE)) {
            throw new ValidatorException("Price should be this format : D.CC");
        }
    }
    
    public static void validateDate(String date) {
        if (!date.matches(REGEX_DATE)) {
            throw new ValidatorException("Date should be this format : DD.MM.YYYY");
        }
    }
    
    public static void validatePhone(String phone) {
        if (!phone.matches(REGEX_PHONE)) {
            throw new ValidatorException("Phone number should be this format : DDDDDDD");
        }
    }
}
