package org.soujava.exchange;

import org.soujava.exchange.ecb.ECBRate;
import org.soujava.exchange.ecb.ECBRepository;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.LocalDate;
import java.util.Random;

@Path("load")
public class LoadResource {

    @Inject
    private ECBRepository repository;

    @POST
    public void load() {
        Random random = new Random();
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("BRL").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("USD").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("PYN").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("EUR").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("COOL").withTime(LocalDate.now()).build());
    }

}