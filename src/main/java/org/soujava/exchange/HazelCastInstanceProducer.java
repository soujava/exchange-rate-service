package org.soujava.exchange;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
class HazelCastInstanceProducer {


    private HazelcastInstance hazelcastInstance;

    @Produces
    public HazelcastInstance get() {
        Config config = new Config();
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        return hazelcastInstance;
    }


    @PreDestroy
    public void destroy() {
        hazelcastInstance.shutdown();
    }
}
