package com.frauddetector.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InputValidator {

    private InputValidator() {
    }

    public static void validate(List<String> transactions, LocalDate date, BigDecimal priceThreshold) {
        Objects.requireNonNull(transactions, "List of transactions must not be null");
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(priceThreshold, "Price threshold must not be null");
    }
}
