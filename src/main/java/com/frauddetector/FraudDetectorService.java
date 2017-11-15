package com.frauddetector;

import com.frauddetector.domain.CreditCardTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


public class FraudDetectorService {

    /**
     * Filters given {@link CreditCardTransaction} by  given date and group them by hashes and prices.
     *
     * @param ccTransactions list of {@link CreditCardTransaction}
     * @param checkDate      check date
     * @return Map (key -> hashed credit card number, value -> List of prices)
     */
    public Map<String, List<BigDecimal>> filterTransactionsByDateAndGroupByHashes(final List<CreditCardTransaction> ccTransactions,
                                                                                  final LocalDate checkDate) {
        validate(ccTransactions, checkDate);

        Map<String, List<BigDecimal>> map = new HashMap<>();

        ccTransactions.stream()
                .filter(n -> n != null)
                .forEach(ccTransaction -> {
                            String hash = ccTransaction.getHashedCreditCardNumber();
                            LocalDate transactionDate = ccTransaction.getDate();
                            if (transactionDate.equals(checkDate)) {

                                BigDecimal transactionPrice = ccTransaction.getPrice();

                                if (!map.containsKey(hash)) {
                                    map.put(hash, newListWith(transactionPrice));
                                } else {
                                    List<BigDecimal> priceList = map.get(hash);
                                    priceList.add(transactionPrice);
                                }
                            }
                        }
                );
        return map;
    }

    private void validate(List<CreditCardTransaction> ccTransactions, LocalDate checkDate) {
        Objects.requireNonNull(ccTransactions, "List of transactions must not be null");
        Objects.requireNonNull(checkDate, "Date must not be null");
    }

    private List<BigDecimal> newListWith(BigDecimal transactionPrice) {
        List<BigDecimal> priceList = new ArrayList<>();
        priceList.add(transactionPrice);
        return priceList;
    }

    /**
     * Finds fraudulent hashed credit card numbers.
     * Credit cards are identified as fraudulent if the sum of prices for a unique hashed credit card number exceeds
     * given price threshold.
     *
     * @param priceThresholdT price threshold
     * @param map             with hashes and prices
     * @return list of fraudulent hashed credit card numbers
     */
    public List<String> findFraudulentHashedCreditCardNumbers(final BigDecimal priceThresholdT,
                                                              final Map<String, List<BigDecimal>> map) {
        validate(priceThresholdT, map);

        List<String> hashes = new ArrayList<>();
        map.forEach((k, v) -> {
            double sum = countSumByHash(v);
            if (isSumGreaterThanThreshold(priceThresholdT, sum)) {
                hashes.add(k);
            }
        });
        return hashes;
    }

    private void validate(BigDecimal priceThresholdT, Map<String, List<BigDecimal>> map) {
        Objects.requireNonNull(priceThresholdT, "priceThresholdT must not be null");
        Objects.requireNonNull(map, "Map must not be null");
    }

    private boolean isSumGreaterThanThreshold(final BigDecimal priceThresholdT, double sum) {
        return priceThresholdT.compareTo(BigDecimal.valueOf(sum)) < 0;
    }

    private double countSumByHash(final List<BigDecimal> list) {
        return list.stream()
                .filter(n -> n != null)
                .mapToDouble(BigDecimal::doubleValue).sum();
    }
}
