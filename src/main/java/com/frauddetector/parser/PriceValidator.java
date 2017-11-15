package com.frauddetector.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceValidator {

    private static final String PRICE_REGEXP = "[0-9]+[.][0-9]{2}";

    private PriceValidator() {
    }

    /**
     * Checks if given price has valid format.
     *
     * @param price price
     * @return true of given price has valid format, otherwise - false
     */
    public static boolean isPriceValid(String price) {
        final Pattern pattern = Pattern.compile(PRICE_REGEXP);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }
}
