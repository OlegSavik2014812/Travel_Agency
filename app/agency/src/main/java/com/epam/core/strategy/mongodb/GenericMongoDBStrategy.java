package com.epam.core.strategy.mongodb;

import com.epam.core.entity.Entity;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * The type Generic mongo db strategy.
 *
 * @param <T> the type parameter
 */
@Profile("mongo")
@Repository
@Scope("prototype")
public class GenericMongoDBStrategy<T extends Entity> implements Strategy<T> {
    private Class<T> parametrizedClass;

    private final MongoTemplate template;
    private String collectionName;
    private final SequenceDao sequenceDao;

    /**
     * Instantiates a new Generic mongo db strategy.
     *
     * @param template    the template
     * @param sequenceDao the sequence dao
     */
    @Autowired
    public GenericMongoDBStrategy(MongoTemplate template, SequenceDao sequenceDao) {
        this.template = template;
        this.sequenceDao = sequenceDao;
    }

    @Override
    public void setParametrizedClass(Class<T> parametrizedClass) {
        this.parametrizedClass = parametrizedClass;
        this.collectionName = parametrizedClass.getSimpleName().toLowerCase();
    }

    @Override
    public void save(T entity) {
        entity.setId(sequenceDao.getNextSequenceId(collectionName));
        template.save(entity, collectionName);
    }

    @Override
    public T findById(Long id) {
        return template.findById(id, parametrizedClass, collectionName);
    }

    @Override
    public T update(T entity) {
        Query query = new Query();
        query.addCriteria(Criteria.where(entity.getId().toString()).is("id"));
        Optional<T> oldEntity = Optional.ofNullable(template.findOne(query, parametrizedClass));
        if (oldEntity.isPresent()) {
            template.remove(oldEntity.get(), collectionName);
            template.save(entity, collectionName);
        } else {
            template.save(entity);
        }
        return template.findById(entity.getId(), parametrizedClass, collectionName);
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<T> optionalEntity = Optional.ofNullable(template.findById(id, parametrizedClass, collectionName));
        if (!optionalEntity.isPresent()) {
            return false;
        }
        template.remove(optionalEntity.get());
        return true;
    }

    @Override
    public List<T> findAll() {
        return template.findAll(parametrizedClass, collectionName);
    }

    @Override
    public List<T> searchEntity(List<SearchCriteria> params) {
        Query query = new Query();
        buildQuery(params, query);
        return template.find(query, parametrizedClass, collectionName);
    }

    @Override
    public Long count() {
        return template.count(new Query(), parametrizedClass);
    }

    private void buildQuery(List<SearchCriteria> params, Query query) {
        for (SearchCriteria param : params) {
            if (!param.getValue().isEmpty()) {
                switch (param.getOperation()) {
                    case (">"): {
                        query.addCriteria(Criteria.where(param.getKey()).gt(param.getValue()));
                        /*predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(param.getKey()), param.getValue()));*/
                        break;
                    }
                    case ("<"): {
                        query.addCriteria(Criteria.where(param.getKey()).lt(param.getValue()));
                        /*predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(param.getKey()), param.getValue()));*/
                        break;
                    }
                    case (":"): {
                        if (param.getKey().contains(".")) {
                            String[] parts = param.getKey().split("\\.");
                            query.addCriteria(Criteria.where(param.getKey()).is(param.getValue()));
                        } else {
                            query.addCriteria(Criteria.where(param.getKey()).is(param.getValue()));
                            /*predicate = builder.and(predicate, builder.like(root.get(param.getKey()), param.getValue()));*/
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
    }
}
