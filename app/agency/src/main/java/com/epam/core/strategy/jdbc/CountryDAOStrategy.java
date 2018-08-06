package com.epam.core.strategy.jdbc;

import com.epam.core.entity.Country;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type Country dao strategy.
 */
@Repository
public class CountryDAOStrategy extends BaseDaoStrategy<Country> implements Strategy<Country> {
    private static final String SQL_CREATE = "INSERT INTO country(name) VALUES (?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM country WHERE country.id = ?";
    private static final String SQL_GET_BY_ID = "SELECT id, name FROM country WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE country SET name = ? WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT id,name from COUNTRY";
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM country";
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Country> rowMapper = (resultSet, i) -> {
        Country country = new Country(resultSet.getString("name"));
        country.setId(resultSet.getLong("id"));
        return country;
    };

    /**
     * Instantiates a new Country dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired(required = false)
    public CountryDAOStrategy(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName(Country.class.getSimpleName().toLowerCase());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Country entity) {
        jdbcTemplate.update(SQL_CREATE, entity.getName());
    }

    @Override
    public Country findById(Long id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, rowMapper, id).get(0);
    }

    @Override
    public Country update(Country entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getId());
        return findById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) == 1;
    }

    @Override
    public List<Country> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, rowMapper);
    }

    @Override
    public List<Country> searchEntity(List<SearchCriteria> params) {
        return null;
    }

    @Override
    public void setParametrizedClass(Class<Country> c) {

    }
}
