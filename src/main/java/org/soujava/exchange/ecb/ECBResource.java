package org.soujava.exchange.ecb;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;
import static org.soujava.exchange.ecb.ECBRateRepresentation.of;

@Path("api/v1/ecb")
@Produces(APPLICATION_JSON)
public class ECBResource {

    @Inject
    private ECBRepository repository;

    @Inject
    private ECBCache cache;

    @GET
    public ECBRateRepresentation getDailyRate() {
        return of(cache.getMostRecent());
    }

    @GET
    @Path("{date}")
    public ECBRateRepresentation getRateFromDay(@PathParam("date") String date) {
        return of(repository.getRates(LocalDate.parse(date)));
    }

    @GET
    @Path("{date1}/{date2}")
    public Response getRateFromDay(@PathParam("date1") String date,
                                   @PathParam("date2") String date2,
                                   @QueryParam("offset") @DefaultValue("0") int offset) {

        ECBResult result = repository.getRates(LocalDate.parse(date), LocalDate.parse(date2), offset);
        return ok(of(result.getRates()))
                .header("X-Total-Count", result.getTotal())
                .header("X-offset", result.getPos()).build();
    }

}
