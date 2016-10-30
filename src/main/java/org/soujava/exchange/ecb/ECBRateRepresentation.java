package org.soujava.exchange.ecb;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ECBRateRepresentation implements Serializable {


    private List<Map<String, List<Item>>> rates = new ArrayList<>();


    public List<Map<String, List<Item>>> getRates() {
        return rates;
    }

    public void setRates(List<Map<String, List<Item>>> rates) {
        this.rates = rates;
    }

    private void add(Map<String, List<Item>> item) {
        rates.add(item);
    }

    public static ECBRateRepresentation of(List<ECBRate> rates) {

        ECBRateRepresentation representation = new ECBRateRepresentation();
        Map<LocalDate, List<ECBRate>> ratesByDate = rates.stream().collect(Collectors.groupingBy(ECBRate::getTime));

        for (LocalDate localDate : ratesByDate.keySet()) {
            representation.add(Collections.singletonMap(localDate.toString(), ratesByDate.get(localDate).stream().map(Item::of).collect(Collectors.toList())));
        }
        return representation;
    }

    private static class Item {

        private String currency;

        private double rate;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public static Item of(ECBRate rate) {
            Item item = new Item();
            item.currency = rate.getCurrency();
            item.rate = rate.getRate();
            return item;
        }
    }
}
