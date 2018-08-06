package com.epam.core.strategy.jdbc;

import com.epam.core.config.JdbcConfig;
import com.epam.core.entity.Tour;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TourDAOStrategyTest {
    private EmbeddedDatabase database;
    private TourDAOStrategy strategy;
    private Tour tour;


    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        tour = (Tour) context.getBean("tour");
        tour.getCountry().setId(2L);
        tour.getHotel().setId(2L);
        tour.getHotel().getCountry().setId(2L);
        tour.setId(2L);
        database = (EmbeddedDatabase) context.getBean("dataSource");
        strategy = (TourDAOStrategy) context.getBean("tourDAOStrategy");
    }

    @Test
    public void create() {
        strategy.save(tour);
        assertNotNull(strategy.findById(3L));
    }

    @Test
    public void getByID() {
        assertNotNull(strategy.findById(2L));
    }

    @Test
    public void update() {
        assertNotNull(strategy.update(tour));
    }

    @Test
    public void delete() {
        assertTrue(strategy.deleteById(2L));
    }

    @After
    public void tearDown() {
        database.shutdown();
    }
}
