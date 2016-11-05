package org.soujava.exchange.ecb;

import org.tomitribe.sabot.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class SQLQuery {

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
}
