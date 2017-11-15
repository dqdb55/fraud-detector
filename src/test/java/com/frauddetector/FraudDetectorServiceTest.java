package com.frauddetector;

import com.frauddetector.domain.CreditCardTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class FraudDetectorServiceTest {

    @Test(expected = NullPointerException.class)
    public void testFilterTransactionsByDateAndGroupByHashes_nullInput() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = null;
        LocalDate checkDate = null;

        //when
        service.filterTransactionsByDateAndGroupByHashes(null, null);

        //then
        //NPE is thrown
    }


    @Test
    public void testFilterTransactionsByDateAndGroupByHashes_emptyListOfTransactions() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        LocalDate checkDate = LocalDate.of(2017, 11, 05);

        //when
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);

        //then
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testFilterTransactionsByDateAndGroupByHashes_listOfTransactionsWithNullValue() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        ccTransactions.add(null);
        LocalDate checkDate = LocalDate.of(2017, 11, 05);

        //when
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);

        //then
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testFilterTransactionsByDateAndGroupByHashes_3transactionWithTheSameCardAndDate() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        LocalDate checkDate = LocalDate.of(2017, 11, 05);
        String hashedCreditCardNumber = "10d7ce2f43e35fa57d1bbf8b1e2";
        ccTransactions.add(new CreditCardTransaction(hashedCreditCardNumber, checkDate, new BigDecimal("10.00")));
        ccTransactions.add(new CreditCardTransaction(hashedCreditCardNumber, checkDate, new BigDecimal
                ("20.00")));
        ccTransactions.add(new CreditCardTransaction(hashedCreditCardNumber, checkDate, new BigDecimal
                ("30.00")));

        //when
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);

        //then
        Assert.assertEquals(1, map.size());
        Assert.assertEquals(3, map.get(hashedCreditCardNumber).size());
    }

    @Test
    public void testFilterTransactionsByDateAndGroupByHashes_transactionDatesSameAsCheckDate() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        LocalDate checkDate = LocalDate.of(2017, 11, 05);
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", checkDate, new BigDecimal("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e3", checkDate, new BigDecimal
                ("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e4", checkDate, new BigDecimal
                ("10.00")));


        //when
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);

        //then
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void
    testFilterTransactionsByDateAndGroupByHashes_3transactionDatesSameAsCheckDateAnd2TransactionDatesDifferent
            () {
        //given
        FraudDetectorService service = new FraudDetectorService();
        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        LocalDate checkDate = LocalDate.of(2017, 11, 05);
        LocalDate checkDate2018 = LocalDate.of(2018, 11, 05);

        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", checkDate, new BigDecimal("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e3", checkDate, new BigDecimal
                ("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e4", checkDate, new BigDecimal
                ("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e5", checkDate2018, new BigDecimal
                ("10.00")));
        ccTransactions.add(new CreditCardTransaction("10d7ce2f43e35fa57d1bbf8b1e6", checkDate2018, new BigDecimal
                ("10.00")));

        //when
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);

        //then
        Assert.assertEquals(3, map.size());
    }


    @Test(expected = NullPointerException.class)
    public void testFindFraudulentHashedCreditCardNumbers_nullInput() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        BigDecimal priceThreshold = null;
        Map<String, List<BigDecimal>> map = null;

        //when
        List<String> fraudulentHashes = service.findFraudulentHashedCreditCardNumbers(priceThreshold, map);

        //then
        //NPE is thrown
    }


    @Test
    public void testFindFraudulentHashedCreditCardNumbers_noFraudulentCards() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        BigDecimal priceThreshold = new BigDecimal("10.00");
        Map<String, List<BigDecimal>> map = new HashMap<>();
        map.put("thisIsVeryComplicatedHash", Arrays.asList(new BigDecimal[]{new BigDecimal("1.00"), new BigDecimal
                ("3.00")}));

        //when
        List<String> fraudulentHashes = service.findFraudulentHashedCreditCardNumbers(priceThreshold, map);

        //then
        Assert.assertEquals(0, fraudulentHashes.size());
    }

    @Test
    public void testFindFraudulentHashedCreditCardNumbers_1FraudulentCard() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        BigDecimal priceThreshold = new BigDecimal("10.00");
        Map<String, List<BigDecimal>> map = new HashMap<>();
        String fraudulentCard = "thisIsVeryComplicatedHash";
        map.put(fraudulentCard, Arrays.asList(new BigDecimal[]{new BigDecimal("8.00"), new BigDecimal
                ("3.00")}));
        map.put("thisIsVeryComplicatedHash2", Arrays.asList(new BigDecimal[]{new BigDecimal("1.00"), new BigDecimal
                ("3.00")}));

        //when
        List<String> fraudulentHashes = service.findFraudulentHashedCreditCardNumbers(priceThreshold, map);

        //then
        Assert.assertEquals(1, fraudulentHashes.size());
        Assert.assertEquals(fraudulentCard, fraudulentHashes.get(0));

    }

    @Test
    public void testFindFraudulentHashedCreditCardNumbers_2FraudulentCards() {
        //given
        FraudDetectorService service = new FraudDetectorService();
        BigDecimal priceThreshold = new BigDecimal("10.00");
        Map<String, List<BigDecimal>> map = new HashMap<>();
        map.put("thisIsVeryComplicatedHash", Arrays.asList(new BigDecimal[]{new BigDecimal("8.00"), new BigDecimal
                ("3.00")}));
        map.put("thisIsVeryComplicatedHash2", Arrays.asList(new BigDecimal[]{new BigDecimal("1.00"), new BigDecimal
                ("30.00")}));

        //when
        List<String> fraudulentHashes = service.findFraudulentHashedCreditCardNumbers(priceThreshold, map);

        //then
        Assert.assertEquals(2, fraudulentHashes.size());

    }
}
