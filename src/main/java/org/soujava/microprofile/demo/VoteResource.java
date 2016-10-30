package org.soujava.microprofile.demo;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@ApplicationScoped
@Path("votes")
@Produces(MediaType.APPLICATION_JSON)
public class VoteResource {


    @Inject
    private VoteCounter voteCounter;

    @GET
    @Path("question")
    public String getQuestion() {
        return "Did you like the post?";
    }

    @POST
    @Path("/yes")
    public void voteYes() {
        voteCounter.voteYes();
    }

    @POST
    @Path("/no")
    public void voteNo() {
        voteCounter.voteNo();
    }

    @GET
    public Map<String, Integer> getResult() {
        return voteCounter.getResult();
    }
}
