package org.soujava.exchange.ecb;

import javax.ejb.Stateless;
import javax.inject.Inject;
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

    @Inject
    private SQLQuery configuration;


    public void save(ECBRate rate) {
        entityManager.persist(rate);
    }

    public List<ECBRate> getRates(LocalDate date) {
        LOGGER.info("finding rates from day: " + date);
        Query query = entityManager.createQuery(configuration.getQueryRate());
        query.setParameter("time", java.sql.Date.valueOf(date));
        return query.getResultList();
    }

    public void save(List<ECBRate> rates) {
        LOGGER.info("Saving rate: " + rates.size());
        rates.forEach(this::save);
    }

    public void update(List<ECBRate> rates) {
        entityManager.merge(rates);
    }

    public List<ECBRate> getRates(LocalDate date, LocalDate date1) {
        Query query = entityManager.createQuery(configuration.getQueryHistoric());
        query.setParameter("time", java.sql.Date.valueOf(date));
        query.setParameter("time1", java.sql.Date.valueOf(date1));
        return query.getResultList();
    }
}
