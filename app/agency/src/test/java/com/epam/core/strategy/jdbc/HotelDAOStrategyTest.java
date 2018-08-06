package com.epam.core.strategy.jdbc;

import com.epam.core.config.JdbcConfig;
import com.epam.core.entity.Country;
import com.epam.core.entity.Hotel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class HotelDAOStrategyTest {
    private EmbeddedDatabase database;
    private HotelDAOStrategy strategy;
    private Hotel hotel;

    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        hotel = (Hotel) context.getBean("hotel");
        hotel.getCountry().setId(1L);
        strategy = (HotelDAOStrategy) context.getBean("hotelDAOStrategy");
        database = (EmbeddedDatabase) context.getBean("dataSource");
    }

    @Test
    public void create() {
        strategy.save(hotel);
        assertNotNull(strategy.findById(3L));
    }

    @Test
    public void getByID() {
        assertNotNull(strategy.findById(2L));
    }

    @Test
    public void update() {
        Country country = new Country("Russia");
        country.setId(2L);
        Hotel hotel = new Hotel();
        hotel.setCountry(country);
        hotel.setName("dfg");
        hotel.setNumberOfStars(3);
        hotel.setId(1L);
        assertNotNull(strategy.update(hotel));
    }

    @Test
    public void delete() {
        assertTrue(strategy.deleteById(1L));
    }

    @After
    public void tearDown() {
        database.shutdown();
    }
}
