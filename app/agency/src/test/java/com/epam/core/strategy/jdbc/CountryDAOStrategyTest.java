package com.epam.core.strategy.jdbc;

import com.epam.core.config.JdbcConfig;
import com.epam.core.entity.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CountryDAOStrategyTest {
    private CountryDAOStrategy strategy;
    private Country country;
    private EmbeddedDatabase database;

    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        strategy = (CountryDAOStrategy) context.getBean("countryDAOStrategy");
        country = (Country) context.getBean("country");
        database = (EmbeddedDatabase) context.getBean("dataSource");
    }

    @Test
    public void create() {
        strategy.save(country);
        assertNotNull(strategy.findById(3L));
    }

    @Test
    public void getByID() {
        assertNotNull(strategy.findById(2L));
    }

    @Test
    public void update() {
        Country country = new Country();
        country.setId(2L);
        country.setName("Russia");
        assertNotNull(strategy.update(country));
    }

    @Test
    public void delete() {
        assertTrue(strategy.deleteById(2L));
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
