package org.soujava.exchange.ecb;

import org.tomitribe.sabot.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class ECBSQuery {

    @Inject
    @Config(value = "query-ecb-rate")
    private String queryRate;


    @Inject
    @Config(value = "query-ecb-historic")
    private String queryHistoric;

    @Inject
    @Config(value = "query-ecb-count")
    private String countQuery;

    @Inject
    @Config(value = "query-ecb-date-most-recent")
    private String dateMostRecent;

    @Inject
    @Config(value = "query-ecb-range-count")
    private String countRange;

    public String getQueryRate() {
        return queryRate;
    }

    public String getQueryHistoric() {
        return queryHistoric;
    }

    public String getCountQuery() {
        return countQuery;
    }

    public String getDateMostRecent() {
        return dateMostRecent;
    }

    public String getCountRange() {
        return countRange;
    }
}
