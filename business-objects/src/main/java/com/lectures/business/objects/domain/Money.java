package com.lectures.business.objects.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Monetary amount with a currency. Immutable, scale-normalised.
 * 
 * Constraints:
 * - There are no double or float fields; use BigDecimal instead.
 * - Arithmetic methods return new Money instances; the class is immutable.
 * - Different currencies cannot be mixed in arithmetic operations.
 * - With setScale, the amount is normalized according to the currency's default fraction digits.
 */
public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {

    private static final Currency TRY = Currency.getInstance("TRY");

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
    }

    public static Money tl(String amount) {
        return new Money(new BigDecimal(amount), TRY);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public Money times(int factor) {
        return new Money(amount.multiply(BigDecimal.valueOf(factor)), currency);
    }

    public Money discountedBy(BigDecimal rate) {
        if (rate.signum() < 0 || rate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("discount rate must be within [0, 1]: " + rate);
        }
        return new Money(amount.multiply(BigDecimal.ONE.subtract(rate)), currency);
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    private void requireSameCurrency(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "currency mismatch: " + currency + " vs " + other.currency);
        }
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + currency.getCurrencyCode();
    }
}
