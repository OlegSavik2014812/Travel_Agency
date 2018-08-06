package com.epam.core.strategy.jpa;

import com.epam.core.config.JpaConfig;
import com.epam.core.entity.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.Assert.*;

public class GenericJpaStrategyTest {
    private GenericJpaStrategy<Country> strategy;
    private Country country;
    private EmbeddedDatabase database;

    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test, jpa");
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        strategy = (GenericJpaStrategy<Country>) context.getBean("genericJpaStrategy");
        strategy.setParametrizedClass(Country.class);
        country = (Country) context.getBean("country");
        database = (EmbeddedDatabase) context.getBean("dataSource");
    }

    @Test()
    public void shouldCreateEntity() {
        strategy.save(country);
        assertNotNull(strategy.findById(3L));
    }

    @Test(expected = Exception.class)
    public void shouldNotCreateEntity() {
        strategy.save(null);
    }

    @Test
    public void shouldGetEntityByID() {
        assertNotNull(strategy.findById(1L));
    }

    @Test(expected = Exception.class)
    public void shouldNotGetEntityByID() {
        strategy.findById(10L);
    }

    @Test
    public void shouldUpdateEntity() {
        assertNotNull(strategy.update(country));
    }

    @Test
    public void shouldDeleteEntityById() {
        assertTrue(strategy.deleteById(1L));
    }

    @Test
    public void shouldNotDeleteEntityById() {
        assertFalse(strategy.deleteById(10L));
    }

    @Test
    public void shouldGetListOfEntities() {
        assertNotNull(strategy.findAll());
    }

    @Test
    public void shouldCount() {
        Long count = strategy.count();
        assertEquals(3L, (long) count);
    }

    @After
    public void tearDown() {
        database.shutdown();
    }
}