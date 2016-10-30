package org.soujava.exchange.ecb;


import org.soujava.exchange.Configuration;
import org.soujava.exchange.Rate;
import org.soujava.exchange.ResourceDownloader;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
@Startup
public class ECBStartup {

    private static Logger LOGGER  = Logger.getLogger(ECBStartup.class.getName());

    @Inject
    private Configuration configuration;

    @Inject
    private ResourceDownloader downloader;

    @Inject
    private ECBRepository repository;

    @PostConstruct
    public void start() throws ParserConfigurationException, SAXException, IOException {
        LOGGER.info("Europe Central Bank starter begging");
        LOGGER.info("Start to download full ECB history");
        byte[] downloaded = downloader.download(configuration.getEcbFullHistory());
        LOGGER.info("Download the ECB full history completed");
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(false);
        saxParserFactory.setValidating(false);
        SAXParser parser = saxParserFactory.newSAXParser();
        InputStream inputStream = new ByteArrayInputStream(downloaded);
        ECBRateReadingHandler handler = new ECBRateReadingHandler();
        parser.parse(inputStream, handler);
        List<ECBRate> rates = handler.getRates();
        LOGGER.info("Found rates size: " + rates.size());
        repository.save(rates);
        LOGGER.info("ECB process finished");

    }
}
