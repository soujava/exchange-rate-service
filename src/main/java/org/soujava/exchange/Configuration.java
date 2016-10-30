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

    public String getEcbDaily() {
        return ecbDaily;
    }

    public String getEcbFullHistory() {
        return ecbFullHistory;
    }
}
