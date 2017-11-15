package com.frauddetector.parser;

import org.junit.Assert;
import org.junit.Test;

public class HashValidatorTest {

    @Test
    public void testIsHashValid_validHash() {
        //given
        String hash = "10d7ce2f43e35fa57d1bbf8b1e2";

        //when
        boolean isValid = HashValidator.isHashValid(hash);

        //then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsHashValid_invalidHash() {
        //given
        String hash = null;

        //when
        boolean isValid = HashValidator.isHashValid(hash);

        //then
        Assert.assertFalse(isValid);
    }


    @Test
    public void testIsHashValid_emptyHash() {
        //given
        String hash = "";

        //when
        boolean isValid = HashValidator.isHashValid(hash);

        //then
        Assert.assertFalse(isValid);
    }
}
