package org.soujava.exchange.ecb;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class ECBCacheTest {


    private ECBCache ecbCache;


    @Before
    public void setUp() {
        ecbCache = new ECBCache();
    }

    @Test
    public void shouldReturnEmptyWhenThereIsNotData() {
        assertTrue(ecbCache.getMostRecent().isEmpty());
    }

    @Test
    public void shouldReturnMostRecentInformation() {
        ECBRate rate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ecbCache.feed(singletonList(rate));
        assertThat(ecbCache.getMostRecent(), contains(rate));
    }

    @Test
    public void shouldReturnDeprecatedWhenThereIsNoData() {
        assertTrue(ecbCache.isCacheDeprecated(LocalDate.now()));
    }

    @Test
    public void shouldNotReturnDeprecatedWhenIsData() {
        ECBRate rate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now()).build();
        ecbCache.feed(singletonList(rate));
        assertFalse(ecbCache.isCacheDeprecated(LocalDate.now()));
    }

    @Test
    public void shouldNotReturnDeprecatedWhenDataIsMostRecent() {
        ECBRate rate = ECBRate.builder().withRate(10D).withCurrency("BRL").withTime(LocalDate.now().minusDays(1L)).build();
        ecbCache.feed(singletonList(rate));
        assertTrue(ecbCache.isCacheDeprecated(LocalDate.now()));
    }
}