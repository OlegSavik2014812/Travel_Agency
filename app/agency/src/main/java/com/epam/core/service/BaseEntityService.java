package com.epam.core.service;

import com.epam.core.entity.Entity;
import com.epam.core.log.ExecutionTime;
import com.epam.core.strategy.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Base entity service.
 *
 * @param <T> the type parameter
 */
@Service
public abstract class BaseEntityService<T extends Entity> implements TravelAgencyService<T, Long> {
    private final Strategy<T> strategy;
    private static final Logger LOGGER = LogManager.getLogger(BaseEntityService.class);

    /**
     * Instantiates a new Base entity service.
     *
     * @param strategy the strategy
     */
    public BaseEntityService(Strategy<T> strategy) {
        this.strategy = strategy;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public boolean add(T entity) {
        try {
            Objects.requireNonNull(entity);
            strategy.save(entity);
            LOGGER.info("Entity  has been created");
            return true;
        } catch (Exception e) {
            LOGGER.warn("Can't save new entity" + e);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public T getById(Long id) {
        try {
            Objects.requireNonNull(id);
            return Optional.ofNullable(strategy.findById(id)).orElseThrow(Exception::new);
        } catch (Exception e) {
            LOGGER.warn("Can't get entity by id " + id);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public T update(T entity) {
        try {
            Objects.requireNonNull(entity);
            return Optional.ofNullable(strategy.update(entity)).orElseThrow(Exception::new);
        } catch (Exception e) {
            LOGGER.warn("Can't update entity" + e);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public boolean delete(Long id) {
        try {
            Objects.requireNonNull(id);
            return strategy.deleteById(id);
        } catch (Exception e) {
            LOGGER.warn("Can't delete entity" + e);
            return false;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @ExecutionTime
    @Override
    public List<T> getList() {
        return Optional.ofNullable(strategy.findAll()).orElse(new ArrayList<>());
    }

    /**
     * Count long.
     *
     * @return the long
     */
    public Long count() {
        return strategy.count();
    }
}
