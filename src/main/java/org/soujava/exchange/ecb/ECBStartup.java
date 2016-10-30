package org.soujava.exchange.ecb;


import org.soujava.exchange.Configuration;
import org.soujava.exchange.ResourceDownloader;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
class ECBStartup {

    private static Logger LOGGER  = Logger.getLogger(ECBStartup.class.getName());

    @Inject
    private Configuration configuration;

    @Inject
    private ResourceDownloader downloader;

    @Inject
    private ECBRepository repository;

    @Inject
    private ECBReader reader;

    @PostConstruct
    public void start() throws ParserConfigurationException, SAXException, IOException {
        LOGGER.info("Europe Central Bank starter begging");
        LOGGER.info("Start to download full ECB history");
        byte[] downloaded = downloader.download(configuration.getEcbFullHistory());
        LOGGER.info("Download the ECB full history completed");
        List<ECBRate> rates = reader.read(downloaded);
        LOGGER.info("Found rates size: " + rates.size());
        repository.save(rates);
        LOGGER.info("ECB process finished");

    }
}
