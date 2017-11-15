package com.frauddetector.parser;

import org.junit.Assert;
import org.junit.Test;

public class PriceValidatorTest {

    @Test
    public void testIsPriceValid_valid1() {
        //given
        String price = "10.00";

        //when
        boolean isValid = PriceValidator.isPriceValid(price);

        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsPriceValid_valid2() {
        //given
        String price = "1000000000.99";

        //when
        boolean isValid = PriceValidator.isPriceValid(price);

        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsPriceValid_invalid1() {
        //given
        String price = "1";

        //when
        boolean isValid = PriceValidator.isPriceValid(price);

        //then
        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsPriceValid_invalid2() {
        //given
        String price = "1,0";

        //when
        boolean isValid = PriceValidator.isPriceValid(price);

        //then
        Assert.assertFalse(isValid);
    }


    @Test
    public void testIsPriceValid_invalid3() {
        //given
        String price = "1,000.00";

        //when
        boolean isValid = PriceValidator.isPriceValid(price);

        //then
        Assert.assertFalse(isValid);
    }
}
