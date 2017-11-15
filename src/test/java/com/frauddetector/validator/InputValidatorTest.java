package com.frauddetector.validator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InputValidatorTest {

    @Test
    public void testValidate_validData_noExceptionThrown() {
        //given
        List<String> transactions = new ArrayList<>();
        LocalDate date = LocalDate.of(2014, 04, 29);
        BigDecimal priceThreshold = BigDecimal.TEN;
        Exception ex = null;

        //when
        try {
            InputValidator.validate(transactions, date, priceThreshold);
        } catch (Exception e) {
            ex = e;
        }

        //then
        Assert.assertNull(ex);
    }

    @Test(expected = NullPointerException.class)
    public void testValidate_nullList() {
        //given
        List<String> transactions = null;
        LocalDate date = LocalDate.of(2014, 04, 29);
        BigDecimal priceThreshold = BigDecimal.TEN;

        //when
        InputValidator.validate(transactions, date, priceThreshold);

        //then
        //NPE is thrown
    }


    @Test(expected = NullPointerException.class)
    public void testValidate_nullDate() {
        //given
        List<String> transactions = new ArrayList<>();
        LocalDate date = null;
        BigDecimal priceThreshold = BigDecimal.TEN;

        //when
        InputValidator.validate(transactions, date, priceThreshold);

        //then
        //NPE is thrown
    }


    @Test(expected = NullPointerException.class)
    public void testValidate_nullThreshold() {
        //given
        List<String> transactions = new ArrayList<>();
        LocalDate date = LocalDate.of(2014, 04, 29);
        BigDecimal priceThreshold = null;

        //when
        InputValidator.validate(transactions, date, priceThreshold);

        //then
        //NPE is thrown
    }
}
