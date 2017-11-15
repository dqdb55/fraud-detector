package com.frauddetector.parser;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateParserTest {

    @Test
    public void testParseDate_correctTimestamp() {
        //given
        String timestamp = "2014-04-29T13:15:54";

        //when
        LocalDate localDate = DateParser.parseDate(timestamp);

        //then
        Assert.assertEquals(2014, localDate.getYear());
        Assert.assertEquals(4, localDate.getMonthValue());
        Assert.assertEquals(29, localDate.getDayOfMonth());
    }


    @Test
    public void testParseDate_incorrectTimestamp() {
        //given
        String timestamp = "2014-04-29?13:15:54";

        //when
        LocalDate localDate = DateParser.parseDate(timestamp);

        //then
        Assert.assertEquals(null, localDate);
    }
}
