package com.frauddetector.parser;

import com.frauddetector.domain.CreditCardTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserTest {

    private final TransactionParser parser = new TransactionParser();


    @Test
    public void testParseTransactionStrings_emptyList() {
        //given
        List<String> transactionStrings = new ArrayList<>();

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertNotNull(creditCardTransactions);
        Assert.assertEquals(0, creditCardTransactions.size());
    }


    @Test(expected = NullPointerException.class)
    public void testParseTransactionStrings_nullList() {
        //given
        List<String> transactionStrings = null;

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        //NPE is thrown
    }

    @Test
    public void testParseTransactionStrings_validData_1Transaction() {
        //given
        String hash = "10d7ce2f43e35fa57d1bbf8b1e1";
        String price = "10.00";

        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add(hash + ", 2014-04-29T13:15:54, " + price);

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(1, creditCardTransactions.size());
        Assert.assertEquals(hash, creditCardTransactions.get(0).getHashedCreditCardNumber());
        Assert.assertEquals(2014, creditCardTransactions.get(0).getDate().getYear());
        Assert.assertEquals(4, creditCardTransactions.get(0).getDate().getMonthValue());
        Assert.assertEquals(29, creditCardTransactions.get(0).getDate().getDayOfMonth());
        Assert.assertEquals(new BigDecimal(price), creditCardTransactions.get(0).getPrice());

    }

    @Test
    public void testParseTransactionStrings_validData_5Transactions() {
        //given
        String hash = "10d7ce2f43e35fa57d1bbf8b1e1";
        String price = "10.00";

        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add(hash + ", 2014-04-29T13:15:54, " + price);
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e3, 2014-01-29T13:15:54, 10.00");
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e4, 2014-02-29T13:15:54, 99.00");
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e5, 2014-03-29T13:15:54, 10.99");
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e6, 2014-04-29T13:15:54, 1000.01");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(5, creditCardTransactions.size());
        Assert.assertEquals(hash, creditCardTransactions.get(0).getHashedCreditCardNumber());
        Assert.assertEquals(2014, creditCardTransactions.get(0).getDate().getYear());
        Assert.assertEquals(4, creditCardTransactions.get(0).getDate().getMonthValue());
        Assert.assertEquals(29, creditCardTransactions.get(0).getDate().getDayOfMonth());
        Assert.assertEquals(new BigDecimal(price), creditCardTransactions.get(0).getPrice());

    }

    @Test
    public void testParseTransactionStrings_validData_additionalWhiteCharacters() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("  10d7ce2f43e35fa57d1bbf8b1e6,  2014-04-29T13:15:54,  10.00   ");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(1, creditCardTransactions.size());
    }

    @Test
    public void testParseTransactionStrings_invalidData_emptyHash() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add(", 2014-04-29T13:15:54, 10.00");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(0, creditCardTransactions.size());
    }

    @Test
    public void testParseTransactionStrings_invalidData_invalidTimestamp() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e6, 2014-04-29?13:15:54, 10.00");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(0, creditCardTransactions.size());
    }


    @Test
    public void testParseTransactionStrings_invalidData_invalidPrice() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e6, 2014-04-29T13:15:54, 10,00");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(0, creditCardTransactions.size());
    }


    @Test
    public void testParseTransactionStrings_invalidData_oneSeparator() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e6, 2014-04-29T13:15:54 10.00");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(0, creditCardTransactions.size());
    }


    @Test
    public void testParseTransactionStrings_invalidData_noSeparators() {
        //given
        List<String> transactionStrings = new ArrayList<>();
        transactionStrings.add("10d7ce2f43e35fa57d1bbf8b1e6 2014-04-29T13:15:54 10.00");

        //when
        List<CreditCardTransaction> creditCardTransactions = parser.parseTransactionStrings(transactionStrings);

        //then
        Assert.assertEquals(0, creditCardTransactions.size());
    }


}
