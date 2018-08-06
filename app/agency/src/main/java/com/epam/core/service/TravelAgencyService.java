package com.epam.core.service;

import com.epam.core.entity.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface Travel agency service.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
public interface TravelAgencyService<T extends Entity, K> {
    /**
     * If the method succeeds, the entity is added to the appropriate repository
     *
     * @param entity {@link Entity}
     * @return true - if method succeeds, false - if method failure
     * @throws ServiceException the service exception
     */
    @Transactional
    boolean add(T entity) throws ServiceException;

    /**
     * If the method succeeds, the entity is deleted from the appropriate repository
     *
     * @param id entity id
     * @return true - if method succeeds, false - if method failure
     */
    @Transactional
    boolean delete(K id);

    /**
     * if the method is successful, then the entity is returned
     *
     * @param id entity id
     * @return {@link Entity} - if method succeeds, null- if method failure
     * @throws ServiceException the service exception
     */
    @Transactional
    T getById(K id) throws ServiceException;


    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     * @throws ServiceException the service exception
     */
    @Transactional
    T update(T entity) throws ServiceException;

    /**
     * Gets list.
     *
     * @return the list
     */
    @Transactional
    List<T> getList();
}
