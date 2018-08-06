package com.epam.core.strategy.jdbc;

import com.epam.core.entity.Entity;
import com.epam.core.strategy.Strategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Base dao strategy.
 *
 * @param <T> the type parameter
 */
@Repository
public abstract class BaseDaoStrategy<T extends Entity> implements Strategy<T> {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    /**
     * Instantiates a new Base dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    public BaseDaoStrategy(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Sets table name.
     *
     * @param tableName the table name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Long count() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
        return (long) (result != null ? result : 0);
    }
}
