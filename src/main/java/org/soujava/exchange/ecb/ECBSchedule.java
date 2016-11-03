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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    @Schedule(minute = "*/1")
    public void sync() throws IOException, SAXException {
        LOGGER.info("Schedule started");
        byte[] downloaded = downloader.download(configuration.getEcbDaily());
        List<ECBRate> ecbRates = reader.read(downloaded);
        Optional<LocalDate> recentDateDownloaded = ecbRates.stream().map(ECBRate::getTime).findFirst();
        if (!recentDateDownloaded.isPresent()) {
            LOGGER.info("ECB rate is empty");
            return;
        }

        if (ecbCache.isCacheDeprecated(recentDateDownloaded.get())) {
            LOGGER.info("Rate is not updated");
            ecbCache.feed(ecbRates);
            repository.save(ecbRates);
        } else {
            mergeRates(ecbRates);

        }

    }

    private void mergeRates(List<ECBRate> ecbRates) {
        List<ECBRate> ratesCached = ecbCache.getMostRecent();
        List<ECBRate> itemsUpdate = ecbRates.stream().filter(e -> !ratesCached.contains(e)).collect(toList());
        if (itemsUpdate.isEmpty()) {
            LOGGER.info("There is not ecb to update on database");
        } else {
            ecbCache.feed(ecbRates);
            repository.update(itemsUpdate);
        }
    }
}
