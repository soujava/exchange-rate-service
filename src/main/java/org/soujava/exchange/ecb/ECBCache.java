package org.soujava.exchange.ecb;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
class ECBCache {

    private static final String ECB_CACHE_NAME = "ecb-cache";

    private static final String MOST_RECENT = "today";

    private Map<String, List<ECBRate>> cache = new ConcurrentHashMap<>();


    @PostConstruct
    void init(){
     //   cache = instance.getMap(ECB_CACHE_NAME);
    }

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
}
