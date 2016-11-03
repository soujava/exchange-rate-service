package org.soujava.exchange.ecb;


import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
class ECBCache {

    private static final String ECB_CACHE_NAME = "ecb-cache";

    private static final String MOST_RECENT = "today";

    private Map<String, List<ECBRate>> cache = new ConcurrentHashMap<>();



    public List<ECBRate> getMostRecent() {
        return cache.getOrDefault(MOST_RECENT, Collections.emptyList());
    }

    public void feed(List<ECBRate> rates) {
        Map<LocalDate, List<ECBRate>> ratesGroupByTime = rates.stream().collect(Collectors.groupingBy(ECBRate::getTime));
        Optional<LocalDate> mostRecent = ratesGroupByTime.keySet().stream().sorted(Comparator.<LocalDate>naturalOrder().reversed()).findFirst();
        mostRecent.ifPresent(localDate -> {
            List<ECBRate> ecbRates = ratesGroupByTime.get(localDate);
            cache.put(MOST_RECENT, ecbRates);
        });
    }

    private Optional<LocalDate> getMostRecentDate() {
        return getMostRecent().stream().map(ECBRate::getTime).findFirst();
    }

    public boolean isCacheDeprecated(LocalDate localDate) {
        Optional<LocalDate> mostRecentDateCached = getMostRecentDate();
        return !mostRecentDateCached.isPresent() || mostRecentDateCached.get().isBefore(localDate);
    }
}
