package org.soujava.exchange.ecb;


import java.util.List;

class ECBResult {

    private final int total;

    private final int pos;

    private final List<ECBRate> rates;

    public ECBResult(int total, int pos, List<ECBRate> rates) {
        this.total = total;
        this.pos = pos;
        this.rates = rates;
    }

    public int getTotal() {
        return total;
    }

    public int getPos() {
        return pos;
    }

    public List<ECBRate> getRates() {
        return rates;
    }


}
