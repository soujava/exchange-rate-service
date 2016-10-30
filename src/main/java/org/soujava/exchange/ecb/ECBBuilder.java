package org.soujava.exchange.ecb;


import java.time.LocalDate;

public final class ECBBuilder {

    private LocalDate time;

    private String currency;

    private double rate;

    ECBBuilder() {
    }

    public ECBBuilder withTime(LocalDate time) {
        this.time = time;
        return this;
    }

    public ECBBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ECBBuilder withRate(double rate) {
        this.rate = rate;
        return this;
    }


    public ECBRate build() {
        return ECBRate.of(ECBId.of(time, currency), rate);
    }
}
