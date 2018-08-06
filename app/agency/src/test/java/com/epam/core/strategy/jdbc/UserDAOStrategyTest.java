package com.epam.core.strategy.jdbc;

import com.epam.core.config.JdbcConfig;
import com.epam.core.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class UserDAOStrategyTest {
    private EmbeddedDatabase database;
    private UserDAOStrategy strategy;
    private User user;


    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        database = (EmbeddedDatabase) context.getBean("dataSource");
        strategy = (UserDAOStrategy) context.getBean("userDAOStrategy");
        user = (User) context.getBean("user");
    }

    @Test
    public void create() {
        strategy.save(user);
        assertNotNull(strategy.findById(3L));
    }

    @Test
    public void getByID() {
        assertNotNull(strategy.findById(1L));
    }

    @Test
    public void update() {
        User user = new User("Oleg", "Savek");
        user.setId(1L);
        assertNotNull(strategy.update(user));
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
