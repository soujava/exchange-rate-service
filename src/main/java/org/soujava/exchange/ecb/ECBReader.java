package org.soujava.exchange.ecb;


import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
class ECBReader {

    private SAXParserFactory saxParserFactory;

    private SAXParser parser;

    @PostConstruct
    void setUp() throws ParserConfigurationException, SAXException {
        saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(false);
        saxParserFactory.setValidating(false);
        parser = saxParserFactory.newSAXParser();
    }

    public List<ECBRate> read(byte[] stream) throws IOException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(stream);
        ECBRateReadingHandler handler = new ECBRateReadingHandler();
        parser.parse(inputStream, handler);
        List<ECBRate> rates = handler.getRates();
        return rates;
    }

}
