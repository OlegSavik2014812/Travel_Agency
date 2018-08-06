
package com.epam.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * The type Jdbc config.
 */
@ComponentScan(basePackages = {"com.epam.core.config.datasource", "com.epam.core.strategy.jdbc", "com.epam.core.entity", "com.epam.core.service", "com.epam.core.log"})
@Import(EntityBeanHolder.class)
@Configuration
public class JdbcConfig {
    /**
     * Jdbc template jdbc template.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
