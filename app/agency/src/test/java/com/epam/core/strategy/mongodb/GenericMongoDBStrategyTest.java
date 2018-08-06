package com.epam.core.strategy.mongodb;

import com.epam.core.config.MongoDBConfig;
import com.epam.core.entity.Sequence;
import com.epam.core.entity.Tour;
import com.epam.core.entity.User;
import com.epam.core.strategy.Strategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GenericMongoDBStrategyTest {
    private Tour tour;
    private GenericMongoDBStrategy<Tour> strategy;
    private MongoTemplate template;
    private GenericMongoDBStrategy<User> userGenericMongoDBStrategy;
    private User user;

    @Before
    public void setUp() {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "mongo");
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoDBConfig.class);
        strategy = (GenericMongoDBStrategy<Tour>) context.getBean("genericMongoDBStrategy");
        strategy.setParametrizedClass(Tour.class);
        tour = (Tour) context.getBean("tour");
        template = (MongoTemplate) context.getBean("mongoTemplate");
        userGenericMongoDBStrategy = (GenericMongoDBStrategy<User>) context.getBean("genericMongoDBStrategy");
        userGenericMongoDBStrategy.setParametrizedClass(User.class);
        user = (User) context.getBean("user");
    }

    @Test
    public void save() {
        strategy.save(tour);
    }

    @Test
    public void findById() {
        strategy.save(tour);
        assertNotNull(strategy.findById(1L));
    }

    @Test
    public void update() {
        tour.setId(1L);
        assertNotNull(strategy.update(tour));
    }

    @Test
    public void deleteById() {
        strategy.save(tour);
        assertTrue(strategy.deleteById(1L));
    }

    @Test
    public void findAll() {
        strategy.save(tour);
        List<Tour> all = strategy.findAll();
        assertNotNull(all);
    }
    @Test
    public void shouldCount() {
        strategy.save(tour);
        Long count = strategy.count();
        assertEquals(1L, (long) count);
    }
    @Test
    public void searchEntity() {
    }

    @After
    public void tearDown() {
        List<Tour> tours = template.findAll(Tour.class, "tour");
        for (Tour tour : tours) {
            template.remove(tour);
        }

        List<Sequence> sequences = template.findAll(Sequence.class, "sequence");
        for (Sequence sequence : sequences) {
            template.remove(sequence);
        }

        template.save(new Sequence("country", 0L));
        template.save(new Sequence("user", 0L));
        template.save(new Sequence("tour", 0L));
        template.save(new Sequence("review", 0L));
        template.save(new Sequence("hotel", 0L));

        userGenericMongoDBStrategy.save(user);
    }
}