package com.frauddetector;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    @Test(expected = NullPointerException.class)
    public void testDetectFraudulentCreditCardNumbers() {
        //given
        Main main = new Main();
        List<String> transactions = null;
        LocalDate checkDate = null;
        BigDecimal priceThresholdT = null;

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        //NPE is thrown
    }

    @Test
    public void testDetectFraudulentCreditCardNumbers_emptyList() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(0, hashes.size());
    }


    @Test
    public void testDetectFraudulentCreditCardNumbers_invalidTransactionString() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54,,, 10.00");
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(0, hashes.size());
    }


    @Test
    public void testDetectFraudulentCreditCardNumbers_0fraudulentCards() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 1.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 2.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 3.00");
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(0, hashes.size());
    }


    @Test
    public void testDetectFraudulentCreditCardNumbers_1fraudulentCard() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 1.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 2.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 8.00");
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(1, hashes.size());
    }


    @Test
    public void testDetectFraudulentCreditCardNumbers_3fraudulentCard() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 1.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 2.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-29T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-29T13:15:54, 9.00");
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(3, hashes.size());
    }

    @Test
    public void testDetectFraudulentCreditCardNumbers_0fraudulentCards_differentDate() {
        //given
        Main main = new Main();
        List<String> transactions = new ArrayList<>();
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-20T13:15:54, 1.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-20T13:15:54, 2.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-20T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-20T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-04-20T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-20T13:15:54, 8.00");
        transactions.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-04-20T13:15:54, 9.00");
        LocalDate checkDate = LocalDate.of(2014, 04, 29);
        BigDecimal priceThresholdT = new BigDecimal("10.00");

        //when
        List<String> hashes = main.detectFraudulentCreditCardNumbers(transactions, checkDate, priceThresholdT);

        //then
        Assert.assertEquals(0, hashes.size());
    }
}
