package com.frauddetector.parser;

import com.frauddetector.domain.CreditCardTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.frauddetector.parser.DateParser.isDateValid;
import static com.frauddetector.parser.DateParser.parseDate;
import static com.frauddetector.parser.HashValidator.isHashValid;
import static com.frauddetector.parser.PriceValidator.isPriceValid;

/**
 * Class parses transaction strings and builds collection of transaction domain objects.
 */
public class TransactionParser {

    private static final Logger logger = LoggerFactory.getLogger(TransactionParser.class);
    private static final int NUMBER_OF_FIELDS_IN_STRING_TRANSACTION = 3;

    /**
     * Parses given transaction strings, validates parsed elements, map them to transaction domain objects
     * {@link CreditCardTransaction} and builds list of these objects.
     *
     * @param transactionStrings list of transaction strings
     * @return list of transaction domain objects
     */
    public List<CreditCardTransaction> parseTransactionStrings(List<String> transactionStrings) {
        Objects.requireNonNull(transactionStrings, "List of transactions must not be null");

        List<CreditCardTransaction> ccTransactions = new ArrayList<>();
        List<String> invalidCCTransactions = new ArrayList<>();

        transactionStrings
                .stream()
                .filter(n -> n != null)
                .forEach(stringTransaction -> {
                    String[] transactionStringValues = stringTransaction.split(",");

                    if (isCorrectNumberOfFieldsInTransactionString(transactionStringValues)) {

                        String hash = transactionStringValues[0].trim();
                        String strDate = transactionStringValues[1].trim();
                        String strPrice = transactionStringValues[2].trim();

                        if (isHashValid(hash) && isDateValid(strDate) && isPriceValid(strPrice)) {

                            CreditCardTransaction creditCardTransaction = buildCreditCardTransaction(hash, strDate, strPrice);
                            ccTransactions.add(creditCardTransaction);
                        } else {
                            gatherInvalidTransactions(invalidCCTransactions, stringTransaction);
                        }
                    } else {
                        gatherInvalidTransactions(invalidCCTransactions, stringTransaction);
                    }
                });

        logger.debug("Invalid transactions: {}", invalidCCTransactions);

        return ccTransactions;
    }

    private CreditCardTransaction buildCreditCardTransaction(String hash, String strDate, String strPrice) {
        LocalDate date = parseDate(strDate);
        BigDecimal price = new BigDecimal(strPrice);
        return new CreditCardTransaction(hash, date, price);
    }

    private boolean isCorrectNumberOfFieldsInTransactionString(String[] strTransactionValues) {
        return strTransactionValues.length == NUMBER_OF_FIELDS_IN_STRING_TRANSACTION;
    }

    private void gatherInvalidTransactions(List<String> invalidCCTransactions, String transactionString) {
        invalidCCTransactions.add(transactionString);
        //It could be nice to do something with invalid records. Send to the user, log to log file ...
        //This is a topic for longer conversation. And it depends on the systems and possibilities you have.
    }


}
