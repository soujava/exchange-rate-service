package org.soujava.exchange;

import org.tomitribe.sabot.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Configuration {

    @Inject
    @Config(value = "ecb-daily")
    private String ecbDaily;

    @Inject
    @Config(value = "ecb-full-history")
    private String ecbFullHistory;

    @Inject
    @Config(value = "query-rate")
    private String queryRate;


    @Inject
    @Config(value = "query-historic")
    private String queryHistoric;


    public String getQueryRate() {
        return queryRate;
    }

    public String getQueryHistoric() {
        return queryHistoric;
    }

    public String getEcbDaily() {
        return ecbDaily;
    }

    public String getEcbFullHistory() {
        return ecbFullHistory;
    }
}
