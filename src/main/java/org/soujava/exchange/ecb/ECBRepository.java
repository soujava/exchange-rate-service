package org.soujava.exchange.ecb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
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

    public List<ECBRate> getRates(LocalDate localDate) {
        LOGGER.info("finding rates from day: " + localDate);
        Query query = entityManager.createQuery("select ecb from ECBRate ecb where ecb.id.time = :time");
        query.setParameter("time", java.sql.Date.valueOf(localDate));
        return query.getResultList();
    }

    public void save(List<ECBRate> rates) {
        rates.forEach(this::save);
    }
}
