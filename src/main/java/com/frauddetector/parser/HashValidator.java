package com.frauddetector.parser;

public class HashValidator {

    private HashValidator() {
    }

    /**
     * Checks if given hash is valid.
     *
     * @param hashedCreditCardNumber hashed credit card number
     * @return true if given hash is valid, otherwise - false.
     */
    public static boolean isHashValid(String hashedCreditCardNumber) {
        //we can add here more conditions depending on hash algorithm
        return hashedCreditCardNumber != null && !hashedCreditCardNumber.equals("");
    }
}
