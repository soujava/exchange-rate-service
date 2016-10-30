/**
 * Copyright (c) 2012, 2015, Credit Suisse (Anatole Tresch), Werner Keil and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.soujava.exchange.ecb;

import org.soujava.exchange.Rate;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ECBRateReadingHandler extends DefaultHandler {

    private LocalDate localDate;

    private final Map<LocalDate, List<Rate>> historicRates = new HashMap<>();



    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if ("Cube".equals(qName)) {
            if (Objects.nonNull(attributes.getValue("time"))) {
                this.localDate = LocalDate.parse(attributes.getValue("time")).atStartOfDay().toLocalDate();
            } else if (Objects.nonNull(attributes.getValue("currency"))) {
                List<Rate> rates = historicRates.computeIfAbsent(localDate, l -> new ArrayList<Rate>());
                String currency = attributes.getValue("currency");
                String rate = attributes.getValue("rate");
                rates.add(new Rate(currency, Double.valueOf(rate)));
            }
        }
        super.startElement(uri, localName, qName, attributes);
    }

    public Map<LocalDate, List<Rate>> getHistoricRates() {
        return historicRates;
    }
}