package com.epam.core.service;

import com.epam.core.entity.Tour;
import com.epam.core.strategy.jpa.GenericJpaStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseEntityServiceTest {
    private TourService service;
    private GenericJpaStrategy<Tour> strategy;
    private Tour entity;

    @Before
    public void setUp() {
        strategy = mock(GenericJpaStrategy.class);
        entity = mock(Tour.class);
        service = new TourService(strategy);
    }

    @Test
    public void shouldAddEntity() {
        assertTrue(service.add(entity));
    }

    @Test
    public void shouldNotAddEntity() {
        assertFalse(service.add(null));
    }

    @Test
    public void shouldDeleteEntity() {
        when(strategy.deleteById(1L)).thenReturn(true);
        assertTrue(service.delete(1L));
    }

    @Test
    public void shouldNotDeleteEntity() {
        assertFalse(service.delete(null));
    }

    @Test
    public void shouldGetEntityById() {
        when(strategy.findById(1L)).thenReturn(entity);
        assertNotNull(service.getById(1L));
    }

    @Test
    public void shouldNotGetEntityById() {
        assertNull(service.getById(0L));
    }

    @Test
    public void shouldUpdateEntity() {
        when(strategy.update(entity)).thenReturn(entity);
        assertNotNull(service.update(entity));
    }

    @Test
    public void shouldGetListOfEntities() {
        assertNotNull(service.getList());
    }
}
