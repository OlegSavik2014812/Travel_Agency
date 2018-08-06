package com.epam.core.strategy.jpa;

import com.epam.core.entity.Entity;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * The type Generic jpa strategy.
 *
 * @param <T> the type parameter
 */
@Profile("jpa")
@Repository
@Scope("prototype")
public class GenericJpaStrategy<T extends Entity> implements Strategy<T> {
    private static final Logger LOGGER = LogManager.getLogger(GenericJpaStrategy.class);
    private Class<T> parametrizedClass;
    @PersistenceContext
    private final EntityManager manager;

    /**
     * Instantiates a new Generic jpa strategy.
     *
     * @param manager the manager
     */
    @Autowired
    public GenericJpaStrategy(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void setParametrizedClass(Class<T> parametrizedClass) {
        this.parametrizedClass = parametrizedClass;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void save(T entity) {
        manager.merge(entity);
    }

    @Override
    public T findById(Long id) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(parametrizedClass);
        Root<T> root = query.from(parametrizedClass);
        query.select(root)
                .where(builder.equal(root.get("id"), id));
        TypedQuery<T> typedQuery = manager.createQuery(query);
        return typedQuery.getSingleResult();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public T update(T entity) {
        return manager.merge(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteById(Long id) {
        Query query = manager.createNativeQuery("delete from " + parametrizedClass.getSimpleName().toLowerCase() + " c where c.id = ? ");
        return query.setParameter(1, id).executeUpdate() == 1;
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(parametrizedClass);
        Root<T> root = query.from(parametrizedClass);
        query.select(root);
        TypedQuery<T> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<T> searchEntity(List<SearchCriteria> params) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = createCriteriaQuery(params, builder);
        return manager.createQuery(query).getResultList();
    }

    @Override
    public Long count() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(parametrizedClass)));
        ParameterExpression<Integer> expression = builder.parameter(Integer.class);
        return manager.createQuery(query).getSingleResult();
    }

    private CriteriaQuery<T> createCriteriaQuery(List<SearchCriteria> params, CriteriaBuilder builder) {
        CriteriaQuery<T> query = builder.createQuery(parametrizedClass);
        Root root = query.from(parametrizedClass);
        Predicate predicate = builder.conjunction();
        for (SearchCriteria param : params) {
            if (!param.getValue().isEmpty()) {
                switch (param.getOperation()) {
                    case (">"): {
                        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(param.getKey()), param.getValue()));
                        break;
                    }
                    case ("<"): {
                        predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(param.getKey()), param.getValue()));
                        break;
                    }
                    case (":"): {
                        if (param.getKey().contains(".")) {
                            String[] parts = param.getKey().split("\\.");
                            predicate = builder.and(predicate, builder.equal(root.get(parts[0]).get(parts[1]), param.getValue()));
                        } else if (root.get(param.getKey()).getJavaType() == String.class) {
                            predicate = builder.and(predicate, builder.like(root.get(param.getKey()), param.getValue()));
                        } else {
                            predicate = builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue()));
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
        query.where(predicate);
        return query;
    }
}

