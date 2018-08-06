package com.epam.core.strategy;

import com.epam.core.entity.Entity;
import com.epam.core.util.SearchCriteria;

import java.util.List;

/**
 * The interface Strategy.
 *
 * @param <E> the type parameter
 */
public interface Strategy<E extends Entity> {
    /**
     * Create boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    void save(E entity);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    E findById(Long id);

    /**
     * Update e.
     *
     * @param entity the entity
     * @return the e
     */
    E update(E entity);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteById(Long id);

    /**
     * Gets list.
     *
     * @return the list
     */
    List<E> findAll();

    /**
     * Search entity list.
     *
     * @param params the params
     * @return the list
     */
    List<E> searchEntity(List<SearchCriteria> params);

    /**
     * Count long.
     *
     * @return the long
     */
    Long count();

    /**
     * Sets parametrized class.
     *
     * @param c the c
     */
    void setParametrizedClass(Class<E> c);
}
