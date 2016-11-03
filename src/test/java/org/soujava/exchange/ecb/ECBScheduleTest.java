package org.soujava.exchange.ecb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.soujava.exchange.Configuration;
import org.soujava.exchange.ResourceDownloader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ECBScheduleTest {

    @Mock
    private Configuration configuration;

    @Mock
    private ResourceDownloader downloader;

    @Mock
    private ECBRepository repository;

    @Mock
    private ECBReader reader;

    @Mock
    private ECBCache ecbCache;

    @Captor
    private ArgumentCaptor<List<ECBRate>> captor;

    @InjectMocks
    private ECBSchedule subject;


    @Before
    public void setUp() {
        when(configuration.getEcbDaily()).thenReturn("ecb-daily");
    }


    @Test
    public void shouldStopWhenDataIsEmpty() throws IOException, SAXException {

        when(reader.read(any(byte[].class))).thenReturn(emptyList());
        when(ecbCache.isCacheDeprecated(any(LocalDate.class))).thenReturn(false);
        subject.sync();
        verify(repository, never()).save(any(ECBRate.class));
    }

    @Test
    public void shouldIgnoreWhenRatesExisted() throws IOException, SAXException {
        ECBRate rate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ECBRate rate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now()).build();

        when(reader.read(any(byte[].class))).thenReturn(asList(rate, rate2));
        when(ecbCache.getMostRecent()).thenReturn(asList(rate, rate2));
        when(ecbCache.isCacheDeprecated(any(LocalDate.class))).thenReturn(false);
        subject.sync();
        verify(repository, never()).save(any(ECBRate.class));
    }


    @Test
    public void shouldSaveWhenRateIsNewDay() throws IOException, SAXException {

        ECBRate rate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ECBRate rate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now()).build();
        ECBRate oldRate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now().minusDays(1)).build();
        ECBRate oldRate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now().minusDays(1)).build();

        when(reader.read(any(byte[].class))).thenReturn(asList(rate, rate2));
        when(ecbCache.isCacheDeprecated(any(LocalDate.class))).thenReturn(true);
        when(ecbCache.getMostRecent()).thenReturn(asList(oldRate, oldRate2));

        subject.sync();
        verify(repository).save(captor.capture());
        List<ECBRate> resultsRepository = captor.getValue();
        verify(ecbCache).feed(captor.capture());
        List<ECBRate> resultsCache = captor.getValue();

        assertThat(resultsRepository, containsInAnyOrder(rate, rate2));
        assertThat(resultsCache, containsInAnyOrder(rate, rate2));
        assertEquals(resultsCache, resultsRepository);
    }

    @Test
    public void shouldUpdateWhenRateIsNew() throws IOException, SAXException {
        ECBRate rate = ECBRate.builder().withRate(12D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ECBRate rate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now()).build();
        ECBRate oldRate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ECBRate oldRate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now()).build();

        when(reader.read(any(byte[].class))).thenReturn(asList(rate, rate2));
        when(ecbCache.isCacheDeprecated(any(LocalDate.class))).thenReturn(false);
        when(ecbCache.getMostRecent()).thenReturn(asList(oldRate, oldRate2));

        subject.sync();
        verify(repository).update(captor.capture());
        List<ECBRate> resultsRepository = captor.getValue();
        verify(ecbCache).feed(captor.capture());
        List<ECBRate> resultsCache = captor.getValue();

        assertThat(resultsRepository, containsInAnyOrder(rate));
        assertThat(resultsCache, containsInAnyOrder(rate, rate2));
    }

    @Test
    public void shouldNotUpdateWhenExchangeIsTheSame() throws IOException, SAXException {
        ECBRate rate = ECBRate.builder().withRate(12D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ECBRate rate2 = ECBRate.builder().withRate(10D).withCurrency("USD").withTime(LocalDate.now()).build();

        when(reader.read(any(byte[].class))).thenReturn(asList(rate, rate2));
        when(ecbCache.isCacheDeprecated(any(LocalDate.class))).thenReturn(false);
        when(ecbCache.getMostRecent()).thenReturn(asList(rate, rate2));

        subject.sync();
        verify(repository, never()).update(captor.capture());
        verify(ecbCache, never()).feed(captor.capture());
    }
}