package org.soujava.exchange.ecb;


import org.soujava.exchange.Configuration;
import org.soujava.exchange.ResourceDownloader;
import org.xml.sax.SAXException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Singleton
class ECBSchedule {


    private static final Logger LOGGER = Logger.getLogger(ECBSchedule.class.getName());

    @Inject
    private Configuration configuration;

    @Inject
    private ResourceDownloader downloader;

    @Inject
    private ECBRepository repository;

    @Inject
    private ECBReader reader;

    @Inject
    private ECBCache ecbCache;

    @Schedule(minute="*/1")
    public void up() throws IOException, SAXException {
        LOGGER.info("Schedule started");
        byte[] downloaded = downloader.download(configuration.getEcbDaily());
        List<ECBRate> ecbRates = reader.read(downloaded);
        Optional<LocalDate> recentDate = ecbRates.stream().map(ECBRate::getTime).findFirst();

    }
}
