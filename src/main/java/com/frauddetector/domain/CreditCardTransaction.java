package com.frauddetector.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class represents credit card transaction domain object.
 */
public class CreditCardTransaction {

    private String hashedCreditCardNumber;
    private LocalDate date;
    private BigDecimal price;

    public CreditCardTransaction(String hashedCreditCardNumber, LocalDate date, BigDecimal price) {
        this.hashedCreditCardNumber = hashedCreditCardNumber;
        this.date = date;
        this.price = price;
    }

    public String getHashedCreditCardNumber() {
        return hashedCreditCardNumber;
    }

    public void setHashedCreditCardNumber(String hashedCreditCardNumber) {
        this.hashedCreditCardNumber = hashedCreditCardNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreditCardTransaction that = (CreditCardTransaction) o;

        if (hashedCreditCardNumber != null ? !hashedCreditCardNumber.equals(that.hashedCreditCardNumber) : that.hashedCreditCardNumber != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        int result = hashedCreditCardNumber != null ? hashedCreditCardNumber.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCardTransaction{");
        sb.append("hashedCreditCardNumber='").append(hashedCreditCardNumber).append('\'');
        sb.append(", date=").append(date);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
