package org.soujava.exchange.ecb;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

}
