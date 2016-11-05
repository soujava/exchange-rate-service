package org.soujava.exchange.ecb;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/ecb")
@Produces(APPLICATION_JSON)
public class ECBResource {

    @Inject
    private ECBRepository repository;

    @Inject
    private ECBCache cache;

    @GET
    public ECBRateRepresentation getDailyRate() {
        return ECBRateRepresentation.of(cache.getMostRecent());
    }

    @GET
    @Path("{date}")
    public ECBRateRepresentation getRateFromDay(@PathParam("date") String date) {
        return ECBRateRepresentation.of(repository.getRates(LocalDate.parse(date)));
    }

    @GET
    @Path("{date1}/{date2}")
    public ECBRateRepresentation getRateFromDay(@PathParam("date1") String date, @PathParam("date2") String date2) {
        return ECBRateRepresentation.of(repository.getRates(LocalDate.parse(date),LocalDate.parse(date2)));
    }

}
