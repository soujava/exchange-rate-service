package org.soujava.exchange.ecb;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;


@Entity
@XmlRootElement
public class ECBRate implements Serializable {

    @EmbeddedId
    private ECBId id;

    @Column
    private double rate;


    public ECBId getId() {
        return id;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ECBRate ecbRate = (ECBRate) o;
        return Objects.equals(id, ecbRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ECBRate{" +
                "id=" + id +
                ", rate=" + rate +
                '}';
    }

    public static ECBRate of(ECBId id, double rate) {
        ECBRate ecb = new ECBRate();
        ecb.id = Objects.requireNonNull(id, "id is required");
        ecb.rate = rate;
        return ecb;
    }
}
