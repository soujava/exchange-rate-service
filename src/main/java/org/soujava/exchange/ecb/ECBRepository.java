package org.soujava.exchange.ecb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class ECBRepository {

    private static final Logger LOGGER = Logger.getLogger(ECBRepository.class.getName());
    private static final int MAX_RESULT = 100;

    @PersistenceContext(unitName = "exchange-unit")
    private EntityManager entityManager;

    @Inject
    private ECBSQuery ecbsQuery;


    public void save(ECBRate rate) {
        entityManager.persist(rate);
    }

    public List<ECBRate> getRates(LocalDate date) {
        LOGGER.info("finding rates from day: " + date);
        Query query = entityManager.createQuery(ecbsQuery.getQueryRate());
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

    public boolean isNotEmpty() {
        Query query = entityManager.createNativeQuery(ecbsQuery.getCountQuery());
        Number count = (Number) query.getSingleResult();
        return count.intValue() > 0;
    }

    private Number getCountRange(LocalDate date, LocalDate date1) {
        Query query = entityManager.createNativeQuery(ecbsQuery.getCountRange());
        query.setParameter(1, java.sql.Date.valueOf(date));
        query.setParameter(2, java.sql.Date.valueOf(date1));
        return (Number) query.getSingleResult();
    }

    public ECBResult getRates(LocalDate date, LocalDate date1, int offset) {
        Query query = entityManager.createQuery(ecbsQuery.getQueryHistoric());
        query.setParameter("time", java.sql.Date.valueOf(date));
        query.setParameter("time1", java.sql.Date.valueOf(date1));
        query.setFirstResult(offset);
        query.setMaxResults(MAX_RESULT);
        int countRange = getCountRange(date, date1).intValue();
        int pos = offset + MAX_RESULT;
        if (pos >= countRange) {
            pos = countRange;
        }
        return new ECBResult(countRange, pos, query.getResultList());
    }

    public List<ECBRate> getMostRecentRates() {
        Query query = entityManager.createQuery(ecbsQuery.getDateMostRecent());
        query.setMaxResults(1);
        Date date = (Date) query.getSingleResult();
        LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
        return getRates(localDate);
    }
}
