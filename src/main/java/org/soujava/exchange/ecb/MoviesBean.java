package org.soujava.exchange.ecb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.List;

@Stateless
public class MoviesBean {

    @PersistenceContext(unitName = "exchange-unit")
    private EntityManager entityManager;

    public Movie find(Long id) {
        return entityManager.find(Movie.class, id);
    }

    public void addMovie(Movie movie) {
        entityManager.persist(movie);
    }

    public void editMovie(Movie movie) {
        entityManager.merge(movie);
    }

    public void deleteMovie(long id) {
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.remove(movie);
    }

    public List<Movie> getMovies(Integer firstResult, Integer maxResults, String field, String searchTerm) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = qb.createQuery(Movie.class);
        Root<Movie> root = cq.from(Movie.class);
        EntityType<Movie> type = entityManager.getMetamodel().entity(Movie.class);
        if (field != null && searchTerm != null && !"".equals(field.trim()) && !"".equals(searchTerm.trim())) {
            Path<String> path = root.get(type.getDeclaredSingularAttribute(field.trim(), String.class));
            Predicate condition = qb.like(path, "%" + searchTerm.trim() + "%");
            cq.where(condition);
        }
        TypedQuery<Movie> q = entityManager.createQuery(cq);
        if (maxResults != null) {
            q.setMaxResults(maxResults);
        }
        if (firstResult != null) {
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public int count(String field, String searchTerm) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<Movie> root = cq.from(Movie.class);
        EntityType<Movie> type = entityManager.getMetamodel().entity(Movie.class);
        cq.select(qb.count(root));
        if (field != null && searchTerm != null && !"".equals(field.trim()) && !"".equals(searchTerm.trim())) {
            Path<String> path = root.get(type.getDeclaredSingularAttribute(field.trim(), String.class));
            Predicate condition = qb.like(path, "%" + searchTerm.trim() + "%");
            cq.where(condition);
        }
        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public void clean() {
        entityManager.createQuery("delete from Movie").executeUpdate();
    }
}