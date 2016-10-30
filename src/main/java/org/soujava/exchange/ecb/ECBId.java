package org.soujava.exchange.ecb;


import org.soujava.exchange.converter.LocalDateConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ECBId implements Serializable {

    @Column
    @Convert(converter = LocalDateConverter.class)
    private Date time;

    @Column
    private String currency;


    public LocalDate getTime() {
        return new java.sql.Date(time.getTime()).toLocalDate();
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ECBId that = (ECBId) o;
        return Objects.equals(time, that.time) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, currency);
    }

    @Override
    public String toString() {
        return "ECBId{" +
                "time=" + time +
                ", currency='" + currency + '\'' +
                '}';
    }

    static ECBId of(LocalDate time, String currency) {
        Objects.requireNonNull(time, "time is required");
        Objects.requireNonNull(currency, "currency is required");
        ECBId id = new ECBId();
        id.currency = currency;
        id.time = java.sql.Date.valueOf(time);
        return id;
    }
}
