package com.epam.core.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * The type Mongo db config.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.epam.core.strategy.mongodb")
@ComponentScan(basePackages = {"com.epam.core.entity", "com.epam.core.log", "com.epam.core.service", "com.epam.core.strategy.mongodb"})
@Import(EntityBeanHolder.class)
public class MongoDBConfig {

    /**
     * Mongo mongo client.
     *
     * @return the mongo client
     * @throws UnknownHostException the unknown host exception
     */
    @Bean
    public MongoClient mongo() throws UnknownHostException {
            return new MongoClient("localhost", 27017);
    }

    /**
     * Mongo template mongo template.
     *
     * @return the mongo template
     * @throws UnknownHostException the unknown host exception
     */
    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongo(), "travel_agency");
    }
}
