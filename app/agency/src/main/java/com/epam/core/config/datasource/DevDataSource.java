package com.epam.core.config.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * The type Dev data source.
 */
@Configuration
@Profile("dev")
public class DevDataSource {
    /**
     * Data source data source.
     *
     * @return the data source
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:8090/travel_agency");
        dataSource.setUsername("postgres");
        dataSource.setPassword("Androhexer1996");
        return dataSource;
    }
}
