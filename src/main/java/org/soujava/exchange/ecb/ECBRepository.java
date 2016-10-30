package org.soujava.exchange.ecb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


@Stateless
public class ECBRepository {

    private static final Logger LOGGER = Logger.getLogger(ECBRepository.class.getName());

    @PersistenceContext(unitName = "exchange-unit")
    private EntityManager entityManager;


    public void save(ECBRate rate) {
        LOGGER.info("Saving new rate: " + rate);
        entityManager.persist(rate);
    }
}
