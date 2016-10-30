package org.soujava.exchange.ecb;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.time.LocalDate;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/ecb")
@Produces(APPLICATION_JSON)
public class ECBResource {

    @Inject
    private ECBRepository repository;

    @GET
    public ECBRateRepresentation getDailyRate() {
        return ECBRateRepresentation.of(repository.getRates(LocalDate.now()));
    }

}
