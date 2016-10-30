package org.soujava.microprofile.demo;


import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class VoteCounter {

    private final AtomicInteger yes = new AtomicInteger();

    private final AtomicInteger no = new AtomicInteger();

    public void voteYes() {
        yes.incrementAndGet();
    }


    public void voteNo() {
        no.incrementAndGet();
    }

    public Map<String, Integer> getResult() {
        Map<String, Integer> votes = new HashMap<>();
        votes.put("yes", yes.get());
        votes.put("no", no.get());
        return votes;
    }


    @Override
    public String toString() {
        return "VoteCounter{" +
                "yes=" + yes +
                ", no=" + no +
                '}';
    }
}
