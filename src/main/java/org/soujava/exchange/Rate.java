package org.soujava.exchange;

import java.util.Objects;

public class Rate {

    private String currency;

    private double rate;

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public Rate(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rate rate1 = (Rate) o;
        return Double.compare(rate1.rate, rate) == 0 &&
                Objects.equals(currency, rate1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, rate);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }
}
