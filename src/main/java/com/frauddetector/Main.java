package com.frauddetector;

import com.frauddetector.domain.CreditCardTransaction;
import com.frauddetector.parser.TransactionParser;
import com.frauddetector.validator.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Main fraud detector class.
 */
public class Main {

    /**
     * Detects fraudulent credit card numbers.
     * A credit card will be identified as fraudulent if the sum of prices for a unique hashed credit card number,
     * for a given day, exceeds the price threshold.
     *
     * @param transactions    list of transaction strings
     * @param checkDate       check date
     * @param priceThresholdT price threshold
     * @return list of detected fraudulent hashed credit card numbers
     */
    public List<String> detectFraudulentCreditCardNumbers(final List<String> transactions, final LocalDate checkDate,
                                                          final BigDecimal priceThresholdT) {
        InputValidator.validate(transactions, checkDate, priceThresholdT);

        List<CreditCardTransaction> ccTransactions = new TransactionParser().parseTransactionStrings(transactions);

        FraudDetectorService service = new FraudDetectorService();
        Map<String, List<BigDecimal>> map = service.filterTransactionsByDateAndGroupByHashes(ccTransactions, checkDate);
        return service.findFraudulentHashedCreditCardNumbers(priceThresholdT, map);
    }


}
