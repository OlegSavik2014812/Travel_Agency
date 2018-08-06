package com.epam.core.strategy.jdbc;

import com.epam.core.config.JdbcConfig;
import com.epam.core.entity.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class ReviewDAOStrategyTest {
    private EmbeddedDatabase database;
    private ReviewDAOStrategy strategy;
    private Review review;


    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        database = (EmbeddedDatabase) context.getBean("dataSource");
        strategy = (ReviewDAOStrategy) context.getBean("reviewDAOStrategy");
        review = (Review) context.getBean("review");
        review.setId(2L);
        review.getTour().setId(1L);
        review.getUser().setId(1L);
    }

    @Test
    public void create() {
        strategy.save(review);
        assertNotNull(strategy.findById(2L));
    }


    @Test
    public void getByID() {
        assertNotNull(strategy.findById(2L));
    }

    @Test
    public void update() {
        assertNotNull(strategy.update(review));
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
