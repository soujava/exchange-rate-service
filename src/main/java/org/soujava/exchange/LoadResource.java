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
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("A").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("B").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("C").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("D").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("E").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("F").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("G").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("H").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("I").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("J").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("L").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("K").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("M").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("N").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("O").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("P").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("Q").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("R").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("S").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("T").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("U").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("V").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("X").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("W").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("Z").withTime(LocalDate.now()).build());
        repository.save(ECBRate.builder().withRate(random.nextDouble()).withCurrency("AB").withTime(LocalDate.now()).build());
    }


}